package com.badger.familyorgbe

import JwtTokenFilter
import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.repository.jwt.JwtRepository
import com.badger.familyorgbe.service.email.ILettersStorage
import com.badger.familyorgbe.service.email.LettersStorage
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.products.ExpirationCheckerUtil
import com.badger.familyorgbe.service.products.IScanningUtil
import com.badger.familyorgbe.service.products.ProductsService
import com.badger.familyorgbe.service.products.ScanningUtil
import com.badger.familyorgbe.service.tasks.INotificationStorage
import com.badger.familyorgbe.service.tasks.NotificationsStorage
import com.badger.familyorgbe.service.users.IOnlineStorage
import com.badger.familyorgbe.service.users.OnlineStorage
import com.badger.familyorgbe.service.users.UserService
import com.badger.familyorgbe.utils.PrepopulateManager
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.jboss.logging.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*
import javax.annotation.PostConstruct

private const val SERVICE_ACCOUNT_NAME = "firebase-service-account.json"
private const val FIREBASE_PROJECT_NAME = "Family organizer"

@SpringBootApplication
@EnableScheduling
class MainApplication {

    @Bean
    fun jwtTokenFilter(): JwtTokenFilter {
        return JwtTokenFilter()
    }

    @Bean
    fun jwtRepository(): IJwtRepository {
        return JwtRepository()
    }

    @Bean
    fun lettersStorage(): ILettersStorage {
        return LettersStorage()
    }

    @Bean
    fun onlineStorage(): IOnlineStorage {
        return OnlineStorage()
    }

    @Bean
    fun notificationsStorage(): INotificationStorage {
        return NotificationsStorage()
    }

    @Bean
    fun scanningUtil(): IScanningUtil {
        return ScanningUtil()
    }

    @PostConstruct
    fun postConstruct() {
        PrepopulateManager().prepopulate()
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging? {
        val googleCredentials = GoogleCredentials
            .fromStream(ClassPathResource(SERVICE_ACCOUNT_NAME).inputStream)

        val firebaseOptions = FirebaseOptions
            .builder()
            .setCredentials(googleCredentials)
            .build()
        val app = FirebaseApp.initializeApp(firebaseOptions, FIREBASE_PROJECT_NAME)

        return FirebaseMessaging.getInstance(app)
    }

}

private val logger by lazy {
    LoggerFactory.logger(MainApplication::class.java)
}

fun infoLog(message: String) = logger.log(Logger.Level.INFO, message)


fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}


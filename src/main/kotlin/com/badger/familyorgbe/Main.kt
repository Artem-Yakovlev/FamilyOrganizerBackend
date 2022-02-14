package com.badger.familyorgbe

import com.badger.familyorgbe.utils.PrepopulateManager
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.jboss.logging.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.annotation.PostConstruct

@SpringBootApplication
class MainApplication {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @PostConstruct
    fun postConstruct() {
        PrepopulateManager().prepopulate()
    }
}

private val logger by lazy {
    LoggerFactory.logger(MainApplication::class.java)
}

fun infoLog(message: String) = logger.log(Logger.Level.INFO, message)

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}
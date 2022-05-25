package com.badger.familyorgbe.service.products

import com.badger.familyorgbe.infoLog
import com.badger.familyorgbe.models.entity.TokenEntity
import com.badger.familyorgbe.models.usual.Product
import com.badger.familyorgbe.service.family.FamilyService
import com.badger.familyorgbe.service.fcm.FirebaseMessagingService
import com.badger.familyorgbe.service.users.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class ExpirationCheckerUtil {

    @Autowired
    private lateinit var productsService: ProductsService

    @Autowired
    private lateinit var familyService: FamilyService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var familyMessagingService: FirebaseMessagingService

    @Autowired
    private lateinit var messageSource: MessageSource

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    @Scheduled(fixedRate = EXPIRATION_CHECK_RATE)
    fun runExpirationChecker() {
        val locale = LocaleContextHolder.getLocale()

        runBlocking {
            familyService.getAllFamilies().forEach { family ->

                coroutineScope.launch {
                    val productsExpiryMillis = productsService.getAllProducts(
                        productsIds = familyService.getAllProductsIdsForFamily(familyId = family.id)
                    ).mapNotNull(Product::expiryMillis)

                    when {
                        productsExpiryMillis.any { expiryMillis -> expiryMillis <= System.currentTimeMillis() } -> {
                            messageSource.getMessage("expiration.body_expired", null, locale)
                        }
                        productsExpiryMillis.any { expiryMillis -> expiryMillis <= CLOSE_TO_EXPIRATION } -> {
                            messageSource.getMessage("expiration.body_close_to_expiration", null, locale)
                        }
                        else -> {
                            null
                        }
                    }?.let { body ->
                        val tokens = userService.getTokensForEmails(family.members).map(TokenEntity::token)
                        tokens.forEach { token ->
                            familyMessagingService.sendNotification(
                                token = token,
                                title = "${messageSource.getMessage("expiration.title", null, locale)} (${family.name})",
                                body = body
                            )

                        }
                    }
                }
            }
        }
    }

    companion object {
        //        private const val EXPIRATION_CHECK_RATE = 1000 * 60 * 60 * 6L
        private const val EXPIRATION_CHECK_RATE = 1000 * 5L
        private const val CLOSE_TO_EXPIRATION = 1000 * 60 * 60 * 36L
    }

}
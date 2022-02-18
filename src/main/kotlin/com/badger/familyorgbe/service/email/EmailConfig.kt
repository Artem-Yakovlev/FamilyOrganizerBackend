package com.badger.familyorgbe.service.email

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class EmailConfig {

    companion object {
        private const val MASTER_EMAIL = "tydeya.noreply@gmail.com"
        private const val MASTER_PASSWORD = "18Fleha?Boo"

        private const val MASTER_HOST = "smtp.gmail.com"
        private const val MASTER_PORT = 587

        private const val SMTH_TRANSPORT_PROTOCOL_KEY = "mail.transport.protocol"
        private const val SMTH_TRANSPORT_PROTOCOL_VALUE = "smtp"

        private const val SMTH_AUTH_KEY = "mail.smtp.auth"
        private const val SMTH_AUTH_VALUE = "true"

        private const val SMTH_STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable"
        private const val SMTH_STARTTLS_ENABLE_VALUE = "true"

        private const val SMTH_DEBUG_KEY = "mail.debug"
        private const val SMTH_DEBUG_VALUE = "true"
    }

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        return JavaMailSenderImpl().apply {
            host = MASTER_HOST
            port = MASTER_PORT

            username = MASTER_EMAIL
            password = MASTER_PASSWORD

            javaMailProperties.apply {
                put(SMTH_TRANSPORT_PROTOCOL_KEY, SMTH_TRANSPORT_PROTOCOL_VALUE)
                put(SMTH_AUTH_KEY, SMTH_AUTH_VALUE)
                put(SMTH_STARTTLS_ENABLE_KEY, SMTH_STARTTLS_ENABLE_VALUE)
                put(SMTH_DEBUG_KEY, SMTH_DEBUG_VALUE)
            }
        }
    }

}
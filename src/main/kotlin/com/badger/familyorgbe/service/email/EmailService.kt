package com.badger.familyorgbe.service.email

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService {

    @Autowired
    private lateinit var storage: ILettersStorage

    @Autowired
    private lateinit var emailSender: JavaMailSender

    suspend fun sendNewCodeTo(email: String): Boolean {
        storage.flushStorage()

        return when (val codeResponse = storage.sendNewCodeTo(email)) {
            is ILettersStorage.SendNewCodeResponse.Success -> {
                val code = codeResponse.code
                val message = SimpleMailMessage().apply {
                    setTo(email)
                    setSubject(AUTH_CODE_SUBJECT)
                    setText(code)
                }
                LoggerFactory.getLogger(EmailService::class.java).warn("code: $code")
//                emailSender.send(message)
                true
            }
            is ILettersStorage.SendNewCodeResponse.Failed -> {
                false
            }
        }
    }

    suspend fun checkCodeForEmail(email: String, code: String): Boolean {
        storage.flushStorage()
        return storage.checkCodeForEmail(email, code)
    }

    companion object {
        private const val AUTH_CODE_SUBJECT = "Код для авторизации"
    }
}
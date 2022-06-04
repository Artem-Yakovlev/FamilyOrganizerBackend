package com.badger.familyorgbe.service.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service


@Service
class FirebaseMessagingService(
    private val firebaseMessaging: FirebaseMessaging
) {

    @Autowired
    private lateinit var messageSource: MessageSource

    fun sendNotification(token: String, title: String, body: String): String {
        val notification: Notification = Notification
            .builder()
            .setTitle(title)
            .setBody(body)
            .build()

        val message: Message = Message
            .builder()
            .setToken(token)
            .setNotification(notification)
            .build()
        return firebaseMessaging.send(message)
    }

    fun sendFailedTaskNotification(token: String, taskTitle: String) {
        val locale = LocaleContextHolder.getLocale()
        val body = messageSource.getMessage("expiration.body_task_failed", null, locale)
        sendNotification(token, taskTitle, body)
    }

    fun sendChangedTaskNotification(token: String, taskTitle: String) {
        val locale = LocaleContextHolder.getLocale()
        val body = messageSource.getMessage("expiration.body_task_changed", null, locale)
        sendNotification(token, taskTitle, body)
    }
}
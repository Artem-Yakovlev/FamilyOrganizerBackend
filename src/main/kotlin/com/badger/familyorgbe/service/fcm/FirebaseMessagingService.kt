package com.badger.familyorgbe.service.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service


@Service
class FirebaseMessagingService(
    private val firebaseMessaging: FirebaseMessaging
) {

    fun sendNotification(token: String): String {
        val notification: Notification = Notification
            .builder()
            .setTitle("Title")
            .setBody("Body")
            .build()

        val message: Message = Message
            .builder()
            .setToken(token)
            .setNotification(notification)
            .putAllData(mapOf("Key" to "Data"))
            .build()
        return firebaseMessaging.send(message)
    }
}
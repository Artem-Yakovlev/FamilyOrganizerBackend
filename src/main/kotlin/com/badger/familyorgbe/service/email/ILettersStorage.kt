package com.badger.familyorgbe.service.email

interface ILettersStorage {

    suspend fun sendNewCodeTo(email: String): SendNewCodeResponse

    suspend fun checkCodeForEmail(email: String, code: String): Boolean

    suspend fun flushStorage()

    sealed class SendNewCodeResponse {
        data class Success(
            val code: String
        ) : SendNewCodeResponse()

        object Failed : SendNewCodeResponse()
    }
}
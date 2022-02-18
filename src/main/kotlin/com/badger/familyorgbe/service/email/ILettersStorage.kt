package com.badger.familyorgbe.service.email

interface ILettersStorage {

    fun sendNewCodeTo(email: String): SendNewCodeResponse

    fun checkCodeForEmail(email: String, code: String): Boolean

    fun flushStorage()

    sealed class SendNewCodeResponse {
        data class Success(
            val code: String
        ) : SendNewCodeResponse()

        object Failed : SendNewCodeResponse()
    }
}
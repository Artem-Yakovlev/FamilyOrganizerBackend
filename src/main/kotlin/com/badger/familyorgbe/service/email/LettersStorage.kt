package com.badger.familyorgbe.service.email

import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.nextInt

class LettersStorage : ILettersStorage {

    private val storage = mutableMapOf<String, LetterCode>()

    override fun sendNewCodeTo(email: String): ILettersStorage.SendNewCodeResponse {
        val existedLetterCode = storage[email]
        val currentNumberOfAttempts = existedLetterCode?.numberOfAttempts ?: 0

        return if (currentNumberOfAttempts < MAX_NUMBER_ATTEMPTS) {
            val code = generateCode()
            val box = LetterCode(
                email = email,
                code = code,
                sentTime = LocalDateTime.now(),
                numberOfAttempts = currentNumberOfAttempts + 1
            )
            storage[email] = box
            ILettersStorage.SendNewCodeResponse.Success(code = code)
        } else {
            ILettersStorage.SendNewCodeResponse.Failed
        }
    }

    override fun checkCodeForEmail(email: String, code: String): Boolean {
        val isApproved = storage[email]?.code == code
        if (isApproved) {
            storage.remove(email)
        }
        return isApproved
    }

    override fun flushStorage() {
        val flushTimeSeconds = LocalDateTime.now().second
        storage.forEach { (key, code) ->
            if (abs(flushTimeSeconds - code.sentTime.second) > MAX_TIME_FOR_LETTER) {
                storage.remove(key)
            }
        }
    }

    private fun generateCode(): String {
        return Random.nextInt(1000..9999).toString()
    }

    private data class LetterCode(
        val email: String,
        val code: String,
        val sentTime: LocalDateTime,
        val numberOfAttempts: Int
    )

    companion object {
        private const val MAX_NUMBER_ATTEMPTS = 5L
        private const val MAX_TIME_FOR_LETTER = 15 * 60L
    }
}
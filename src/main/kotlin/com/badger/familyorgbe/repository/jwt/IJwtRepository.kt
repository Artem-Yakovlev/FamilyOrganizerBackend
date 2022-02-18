package com.badger.familyorgbe.repository.jwt

interface IJwtRepository {

    fun generateToken(email: String): String

    fun validateToken(token: String): Boolean

    fun getEmail(token: String): String
}
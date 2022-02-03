package com.badger.familyorgbe.models.reponse

data class UserResponse(
    val id: Long?,
    private val name: String,
    private val email: String
)
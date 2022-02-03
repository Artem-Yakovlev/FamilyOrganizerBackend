package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.Role
import com.badger.familyorgbe.models.entity.UserEntity

data class User(
    val name: String,
    private val email: String,
    val password: String
) {
    fun toEntity(
        roles: Set<Role>,
        encodedPassword: String
    ) = UserEntity(
        id = null,
        name = name,
        email = email,
        password = encodedPassword,
        roles = roles
    )
}
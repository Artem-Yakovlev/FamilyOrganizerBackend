package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.UserEntity

data class User(
    val name: String,
    val email: String,
    val password: String
) {
    fun toEntity(
        encodedPassword: String
    ) = UserEntity(
        id = null,
        name = name,
        email = email,
        password = encodedPassword
    )

    companion object {
        fun fromEntity(entity: UserEntity) = User(
            name = entity.name,
            email = entity.email,
            password = entity.password
        )
    }
}
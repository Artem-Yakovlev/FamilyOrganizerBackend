package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.UserEntity

data class User(
    val name: String,
    val email: String,
    val fridgeId: String
) {
    fun toEntity(
    ) = UserEntity(
        name = name,
        email = email
    )

    companion object {

        fun createEmpty(email: String) = User(
            name = "",
            email = email,
            fridgeId = ""
        )

        fun fromEntity(entity: UserEntity) = User(
            name = entity.name,
            email = entity.email,
            fridgeId = "NONE"
        )
    }
}
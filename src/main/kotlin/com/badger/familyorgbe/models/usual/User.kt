package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.UserEntity
import com.badger.familyorgbe.models.entity.UserStatus
import javax.persistence.Column

data class User(
    val name: String,
    val email: String,
    val imagePath: String?,
    val status: UserStatus
) {
    fun toEntity(
    ) = UserEntity(
        name = name,
        email = email,
        status = status
    )

    companion object {
        fun createEmpty(email: String) = User(
            name = "",
            email = email,
            imagePath = null,
            status = UserStatus.UNDEFINED
        )

        fun fromEntity(entity: UserEntity) = User(
            name = entity.name,
            email = entity.email,
            imagePath = entity.imagePath,
            status = entity.status
        )
    }
}
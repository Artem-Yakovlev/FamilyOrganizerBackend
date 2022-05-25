package com.badger.familyorgbe.repository.users

import com.badger.familyorgbe.models.entity.UserEntity
import com.badger.familyorgbe.models.entity.UserStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface IUsersRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?

    @Modifying
    @Query("update UserEntity u set u.name = :name where u.email = :email")
    fun updateName(
        @Param(value = "email") email: String,
        @Param(value = "name") name: String
    )

    @Modifying
    @Query("update UserEntity u set u.status = :status where u.email = :email")
    fun updateStatus(
        @Param(value = "email") email: String,
        @Param(value = "status") status: UserStatus
    )

    @Query("select u from UserEntity u where u.email in :emails")
    fun getAllByEmails(
        @Param(value = "emails") emails: List<String>
    ): List<UserEntity>

    @Modifying
    @Query("update UserEntity u set u.imagePath = :imagePath where u.email = :email")
    fun updateImagePath(
        @Param(value = "email") email: String,
        @Param(value = "imagePath") imagePath: String
    )
}
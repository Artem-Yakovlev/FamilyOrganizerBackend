package com.badger.familyorgbe.repository.users

import com.badger.familyorgbe.models.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface IUsersRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?

    @Modifying
    @Query("UPDATE Users set name = :username where id = :userId")
    fun updateUserName(userId: Long, username: String): Boolean
}
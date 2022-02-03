package com.badger.familyorgbe.repository.users

import com.badger.familyorgbe.models.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IUsersRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?
}
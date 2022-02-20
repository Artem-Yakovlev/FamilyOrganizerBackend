package com.badger.familyorgbe.repository.users

import com.badger.familyorgbe.models.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface IUsersRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?

    @Modifying
    @Query("update UserEntity u set u.name = :name where u.id = :id")
    fun updateName(
        @Param(value = "id") id: Long,
        @Param(value = "name") name: String
    )
}
package com.badger.familyorgbe.repository.token

import com.badger.familyorgbe.models.entity.TokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ITokenRepository : JpaRepository<TokenEntity, Long> {

    @Query("select token from TokenEntity token where token.email in :emails")
    fun getAllByEmails(
        @Param(value = "emails") emails: List<String>
    ): List<TokenEntity>
}
package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "tokens")
class TokenEntity(

    @Id
    @Column(name = "email")
    val email: String,

    @Column(name = "token")
    val token: String
)
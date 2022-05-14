package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "email")
    val email: String,
    @Column(name = "name")
    val name: String
) {

    fun toUserDetails() = org.springframework.security.core.userdetails.User(
        email,
        "",
        true,
        true,
        true,
        true,
        emptySet()
    )
}
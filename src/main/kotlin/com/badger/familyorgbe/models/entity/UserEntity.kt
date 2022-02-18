package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name")
    val name: String,
    @Column(name = "email")
    val email: String,
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
package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,
    @Column(name = "name")
    val name: String,
    @Column(name = "email")
    val email: String,
    @Column(name = "password")
    val password: String
) {
    fun toUserDetails() = org.springframework.security.core.userdetails.User(
        email,
        password,
        true,
        true,
        true,
        true,
        emptySet()
    )
}
package com.badger.familyorgbe.models.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "email")
    val email: String,

    @Column(name = "name")
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: UserStatus
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

enum class UserStatus {
    UNDEFINED,
    AT_HOME,
    AT_SHOP,
    AT_WALK,
    AT_WORK,
    IN_ROAD
}
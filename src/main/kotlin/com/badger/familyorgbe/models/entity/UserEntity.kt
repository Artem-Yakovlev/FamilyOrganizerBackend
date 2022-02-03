package com.badger.familyorgbe.models.entity

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,
    @Column(name = "name")
    private val name: String,
    @Column(name = "email")
    private val email: String,
    @Column(name = "password")
    private val password: String,
    @ManyToMany(fetch = FetchType.EAGER)
    private val roles: Set<Role>?

) : UserDetails {

    override fun getAuthorities(): Set<Role>? {
        return roles
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
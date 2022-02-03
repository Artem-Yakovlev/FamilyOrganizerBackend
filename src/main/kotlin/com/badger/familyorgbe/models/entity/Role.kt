package com.badger.familyorgbe.models.entity

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "roles")
class Role(
    @Id
    @Column(name = "id")

    private val id: Long,
    @Column(name = "name")
    private val name: String,

    @Transient
    @ManyToMany(mappedBy = "roles")
    private val users: List<UserEntity> = emptyList()

) : GrantedAuthority {

    override fun getAuthority(): String {
        return name
    }
}
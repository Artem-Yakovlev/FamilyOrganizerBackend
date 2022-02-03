package com.badger.familyorgbe.repository.roles

import com.badger.familyorgbe.models.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface IRolesRepository : JpaRepository<Role, Long> {
}
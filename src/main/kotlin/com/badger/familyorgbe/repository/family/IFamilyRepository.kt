package com.badger.familyorgbe.repository.family

import com.badger.familyorgbe.models.entity.FamilyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IFamilyRepository : JpaRepository<FamilyEntity, Long> {
}
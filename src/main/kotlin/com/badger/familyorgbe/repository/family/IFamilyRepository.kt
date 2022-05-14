package com.badger.familyorgbe.repository.family

import com.badger.familyorgbe.models.entity.FamilyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface IFamilyRepository : JpaRepository<FamilyEntity, Long> {

    @Query(value = "select fam from FamilyEntity fam where :email in fam.members or :email in fam.invites")
    fun getAllFamiliesForEmail(
        @Param(value = "email") email: String
    ): List<FamilyEntity>

    @Query(value = "select fam from FamilyEntity fam where :id = fam.id")
    fun getFamilyById(
        @Param(value = "id") id: Long
    ): FamilyEntity?
}
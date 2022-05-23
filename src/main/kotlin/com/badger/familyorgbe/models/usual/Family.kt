package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.FamilyEntity
import com.badger.familyorgbe.utils.converters.convertToEmailList
import com.badger.familyorgbe.utils.converters.convertToEmailString


data class Family(
    val id: Long,
    val name: String,
    val members: List<String>,
    val invites: List<String>
) {

    fun toEntity() = FamilyEntity(
        name = name,
        members = convertToEmailString(members),
        invites = convertToEmailString(invites)
    )

    companion object {

        fun fromEntity(entity: FamilyEntity) = with(entity) {
            Family(
                id = id,
                name = name,
                members = convertToEmailList(members),
                invites = convertToEmailList(invites)
            )
        }
    }
}
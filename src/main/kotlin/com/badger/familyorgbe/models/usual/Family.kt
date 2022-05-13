package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.FamilyEntity


data class Family(
    val id: String,
    val name: String,
    val fridgeId: String,
    val members: List<String>,
    val invites: List<String>
) {

    fun toEntity() = FamilyEntity(
        name = name,
        fridgeId = fridgeId
    )

    companion object {

        fun createEmpty(creatorEmail: String) = Family(
            id = "",
            name = "",
            fridgeId = "",
            members = listOf(creatorEmail),
            invites = emptyList()
        )

        fun fromEntity(entity: FamilyEntity) = Family(
            id = entity.id.toString(),
            name = entity.name,
            fridgeId = entity.fridgeId,
            members = emptyList(),
            invites = emptyList()
        )
    }
}
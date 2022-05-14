package com.badger.familyorgbe.models.usual

import com.badger.familyorgbe.models.entity.FamilyEntity


data class Family(
    val id: Long,
    val name: String,
//    val fridgeId: String,
    val members: List<String>,
    val invites: List<String>
) {

    fun toEntity() = FamilyEntity(
        name = name,
//        fridgeId = fridgeId,
        members = members,
        invites = invites
    )

    companion object {

        fun fromEntity(entity: FamilyEntity) = with(entity){
            Family(
                id = id,
                name = name,
//                fridgeId = fridgeId,
                members = members,
                invites = invites
            )
        }
    }
}
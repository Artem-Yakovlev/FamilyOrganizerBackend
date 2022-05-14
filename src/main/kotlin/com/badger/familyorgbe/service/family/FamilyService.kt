package com.badger.familyorgbe.service.family

import com.badger.familyorgbe.models.entity.FamilyEntity
import com.badger.familyorgbe.models.usual.Family
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.repository.users.IUsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FamilyService {

    @Autowired
    private lateinit var familyRepository: IFamilyRepository

    @Autowired
    private lateinit var usersRepository: IUsersRepository

    fun createFamily(authorEmail: String, familyName: String): Family {
        val entity = FamilyEntity(
            name = familyName,
            members = listOf(authorEmail),
            invites = emptyList()
        )
        val savedEntity = familyRepository.save(entity)
        return Family.fromEntity(savedEntity)
    }

    fun getAllFamiliesForEmail(email: String): List<Family> {
        return familyRepository
            .getAllFamiliesForEmail(email)
            .map(Family::fromEntity)
    }

    fun getFamilyById(id: Long): Family? {
        val entity = familyRepository.getFamilyById(id)
        return entity?.let(Family::fromEntity)
    }

    fun excludeMemberFromFamily(familyId: Long, email: String): Family? {
        return familyRepository.getFamilyById(familyId)?.let { entity ->
            val updatedEntity = entity.copy(
                members = entity.members.filter { memberEmail -> memberEmail != email },
                invites = entity.invites.filter { inviteEmail -> inviteEmail != email }
            )
            val savedEntity = familyRepository.save(updatedEntity)
            Family.fromEntity(savedEntity)
        }
    }

    fun getAllMembersForFamily(familyId: Long): List<User> {
        return familyRepository.getFamilyById(familyId)
            ?.let { family ->
//                usersRepository
//                    .getAllByEmails(family.members)
//                    .map(User::fromEntity)
                emptyList()
            } ?: emptyList()
    }
}
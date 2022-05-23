package com.badger.familyorgbe.service.family

import com.badger.familyorgbe.models.entity.FamilyEntity
import com.badger.familyorgbe.models.usual.Family
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.repository.users.IUsersRepository
import com.badger.familyorgbe.utils.converters.convertToEmailList
import com.badger.familyorgbe.utils.converters.convertToEmailString
import com.badger.familyorgbe.utils.converters.convertToIdsList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FamilyService {

    @Autowired
    private lateinit var familyRepository: IFamilyRepository

    @Autowired
    private lateinit var usersRepository: IUsersRepository

    fun createFamily(authorEmail: String, familyName: String): Pair<Long, List<Family>> {
        val entity = FamilyEntity(
            name = familyName,
            members = authorEmail,
            invites = "",
            productsIds = ""
        )
        val savedEntity = familyRepository.save(entity)
        return savedEntity.id to getAllFamiliesForEmail(authorEmail)
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
            val members = convertToEmailList(entity.members).filter { memberEmail -> memberEmail != email }
            val invites = convertToEmailList(entity.invites).filter { inviteEmail -> inviteEmail != email }

            val updatedEntity = entity.copy(
                members = convertToEmailString(members),
                invites = convertToEmailString(invites)
            )
            val savedEntity = familyRepository.save(updatedEntity)
            Family.fromEntity(savedEntity)
        }
    }

    fun getAllMembersForFamily(familyId: Long): List<User> {
        return familyRepository.getFamilyById(familyId)
            ?.let { family ->
                usersRepository
                    .getAllByEmails(convertToEmailList(family.members))
                    .map(User::fromEntity)
            } ?: emptyList()
    }

    fun getAllProductsIdsForFamily(familyId: Long): List<Long> {
        return familyRepository.getFamilyById(familyId)?.productsIds?.convertToIdsList() ?: emptyList()
    }
}
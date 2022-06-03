package com.badger.familyorgbe.service.family

import com.badger.familyorgbe.core.base.rest.ResponseError
import com.badger.familyorgbe.models.entity.FamilyEntity
import com.badger.familyorgbe.models.usual.Family
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.family.IFamilyRepository
import com.badger.familyorgbe.repository.users.IUsersRepository
import com.badger.familyorgbe.utils.converters.convertToEmailList
import com.badger.familyorgbe.utils.converters.convertToEmailString
import com.badger.familyorgbe.utils.converters.convertToIdsList
import kotlinx.coroutines.Dispatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FamilyService {

    @Autowired
    private lateinit var familyRepository: IFamilyRepository

    @Autowired
    private lateinit var usersRepository: IUsersRepository

    suspend fun getAllFamilies(): List<Family> {
        return with(Dispatchers.IO) {
            familyRepository.getAll().map(Family::fromEntity)
        }
    }

    suspend fun createFamily(authorEmail: String, familyName: String): Pair<Long, List<Family>> {
        val entity = FamilyEntity(
            name = familyName,
            members = authorEmail,
            invites = "",
            productsIds = "",
            tasks = emptyList()
        )
        val savedEntity = with(Dispatchers.IO) { familyRepository.save(entity) }
        return savedEntity.id to getAllFamiliesForEmail(authorEmail)
    }

    suspend fun getAllFamiliesForEmail(email: String): List<Family> {
        return with(Dispatchers.IO) {
            familyRepository.getAllFamiliesForEmail(email)
        }.map(Family::fromEntity)
    }

    suspend fun getFamilyById(id: Long): Family? {
        val entity = with(Dispatchers.IO) { familyRepository.getFamilyById(id) }
        return entity?.let(Family::fromEntity)
    }

    suspend fun excludeMemberFromFamily(familyId: Long, email: String): Family? {
        return with(Dispatchers.IO) { familyRepository.getFamilyById(familyId) }?.let { entity ->
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

    fun inviteMemberToFamily(familyId: Long, email: String): ResponseError? {
        return familyRepository.getFamilyById(familyId)?.let { entity ->
            if (entity.members.contains(email)) {
                return ResponseError.USER_ALREADY_IN_FAMILY
            }

            if (entity.invites.contains(email)) {
                return ResponseError.USER_ALREADY_INVITED
            }

            val invites = convertToEmailList(entity.invites).filter { inviteEmail -> inviteEmail != email }

            val updatedEntity = entity.copy(
                invites = convertToEmailString(invites + email)
            )
            familyRepository.save(updatedEntity)
            null
        }
    }

    suspend fun getAllMembersForFamily(familyId: Long): List<User> {
        return with(Dispatchers.IO) { familyRepository.getFamilyById(familyId) }
            ?.let { family ->
                usersRepository
                    .getAllByEmails(convertToEmailList(family.members))
                    .map(User::fromEntity)
            } ?: emptyList()
    }

    suspend fun getAllProductsIdsForFamily(familyId: Long): List<Long> {
        return with(Dispatchers.IO) { familyRepository.getFamilyById(familyId) }
            ?.productsIds?.convertToIdsList()
            ?: emptyList()
    }
}
package com.badger.familyorgbe.service.users

import com.badger.familyorgbe.models.entity.UserEntity
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.users.IUsersRepository
import com.badger.familyorgbe.utils.RolesHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: IUsersRepository

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(username: String): UserDetails? {
        return null
    }

    fun saveUser(user: User): Boolean {
        return if (userRepository.findByEmail(user.name) != null) {
            false
        } else {
            val entity = user.toEntity(
                roles = setOf(RolesHelper.userRole),
                encodedPassword = bCryptPasswordEncoder.encode(user.password)
            )
            userRepository.save(entity)
            true
        }
    }
}
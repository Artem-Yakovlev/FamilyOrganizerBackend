package com.badger.familyorgbe.service.users

import com.badger.familyorgbe.models.entity.UserEntity
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.users.IUsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: IUsersRepository

    fun saveUser(user: User): Boolean {
        return if (userRepository.findByEmail(user.name) != null) {
            false
        } else {
            val entity = user.toEntity()
            userRepository.save(entity)
            true
        }
    }

    fun findUserByEmail(email: String) = userRepository.findByEmail(email = email)

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = userRepository.findByEmail(email)
        if (userEntity != null) {
            return userEntity.toUserDetails()
        } else {
            throw UsernameNotFoundException("User $email not found")
        }
    }

    @Transactional
    fun updateNameOfUser(email: String, name: String): User? {
        return findUserByEmail(email)?.let { user ->
            userRepository.updateName(
                email = user.email,
                name = name
            )
            User.fromEntity(user).copy(name = name)
        }
    }
}
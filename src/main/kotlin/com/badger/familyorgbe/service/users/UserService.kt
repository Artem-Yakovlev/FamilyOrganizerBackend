package com.badger.familyorgbe.service.users

import com.badger.familyorgbe.models.entity.TokenEntity
import com.badger.familyorgbe.models.entity.UserStatus
import com.badger.familyorgbe.models.usual.User
import com.badger.familyorgbe.repository.token.ITokenRepository
import com.badger.familyorgbe.repository.users.IUsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    @Autowired
    private lateinit var tokenRepository: ITokenRepository

    suspend fun saveUser(user: User): Boolean {
        return if (with(Dispatchers.IO) { userRepository.findByEmail(user.name) } != null) {
            false
        } else {
            val entity = user.toEntity()
            with(Dispatchers.IO) {
                userRepository.save(entity)
            }
            true
        }
    }

    fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email = email)?.let(User::fromEntity)
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity = userRepository.findByEmail(email)
        if (userEntity != null) {
            return userEntity.toUserDetails()
        } else {
            throw UsernameNotFoundException("User $email not found")
        }
    }

    @Transactional
    suspend fun updateNameOfUser(email: String, name: String): User? {
        return findUserByEmail(email)?.let { user ->
            userRepository.updateName(
                email = user.email,
                name = name
            )
            user.copy(name = name)
        }
    }

    @Transactional
    suspend fun updateStatusOfUser(email: String, status: UserStatus): User? {
        return findUserByEmail(email)?.let { user ->
            userRepository.updateStatus(
                email = user.email,
                status = status
            )
            user.copy(status = status)
        }
    }

    suspend fun getTokensForEmails(emails: List<String>): List<TokenEntity> {
        return with(Dispatchers.IO) { tokenRepository.getAllByEmails(emails) }
    }

    suspend fun saveToken(email: String, token: String) {
        val entity = TokenEntity(email, token)
        with(Dispatchers.IO) { tokenRepository.save(entity) }
    }

    @Transactional
    suspend fun saveImageAddress(email: String, imagePath: String) {
        with(Dispatchers.IO) { userRepository.updateImagePath(email = email, imagePath = imagePath) }
    }
}
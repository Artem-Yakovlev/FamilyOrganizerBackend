package com.badger.familyorgbe.core.base

import com.badger.familyorgbe.repository.jwt.IJwtRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class BaseAuthedController : BaseController() {

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    fun String.getAuthEmail(): String {
        val token = getBearerTokenIfExist()
        return jwtRepository.getEmail(token)
    }
}
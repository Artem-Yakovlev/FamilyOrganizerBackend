package com.badger.familyorgbe.core.base

import com.badger.familyorgbe.security.getTokenFromBearer
import com.badger.familyorgbe.security.isBearer
import org.springframework.security.authentication.AuthenticationServiceException

open class BaseController {

    fun String.getBearerTokenIfExist(): String {
        return if (isBearer()) {
            getTokenFromBearer() ?: throwAuthenticationServiceException()
        } else {
            throwAuthenticationServiceException()
        }
    }

    private fun throwAuthenticationServiceException(): Nothing {
        throw AuthenticationServiceException("Bearer is invalid")
    }
}
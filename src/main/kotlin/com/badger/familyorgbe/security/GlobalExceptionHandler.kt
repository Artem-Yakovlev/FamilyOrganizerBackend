package com.badger.familyorgbe.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.security.web.csrf.InvalidCsrfTokenException
import org.springframework.security.web.csrf.MissingCsrfTokenException
import org.springframework.security.web.util.UrlUtils
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestControllerAdvice
class GlobalExceptionHandler() : ResponseEntityExceptionHandler() {

    companion object {
        private const val ERROR_AUTHORIZATION = "error.authorization"
    }

    data class ErrorInfo(
        val url: String,
        val info: String
    )

    @ExceptionHandler(
        AuthenticationException::class,
        MissingCsrfTokenException::class,
        InvalidCsrfTokenException::class,
        SessionAuthenticationException::class
    )
    fun handleAuthenticationException(
        ex: RuntimeException?,
        request: HttpServletRequest?,
        response: HttpServletResponse
    ): ErrorInfo {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        return ErrorInfo(UrlUtils.buildFullRequestUrl(request), ERROR_AUTHORIZATION)
    }
}
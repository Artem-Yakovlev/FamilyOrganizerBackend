package com.badger.familyorgbe.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.DefaultCsrfToken
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Repository
class JwtTokenRepository : CsrfTokenRepository {

    override fun generateToken(httpServletRequest: HttpServletRequest): CsrfToken {
        val id = UUID.randomUUID().toString().replace("-", "")
        val now = Date()
        val exp: Date = Date.from(
            LocalDateTime.now().plusYears(2)
                .atZone(ZoneId.systemDefault()).toInstant()
        )
        var token = ""
        try {
            token = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SecurityConsts.JWT_SECRET)
                .compact()
        } catch (e: JwtException) {
            e.printStackTrace()
            //ignore
        }
        return DefaultCsrfToken(SecurityConsts.X_CSRF_TOKEN, "_csrf", token)
    }

    override fun saveToken(csrfToken: CsrfToken, request: HttpServletRequest?, response: HttpServletResponse) {
        if (!response.headerNames.contains(ACCESS_CONTROL_EXPOSE_HEADERS)) {
            response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, csrfToken.headerName)
        }
        if (response.headerNames.contains(csrfToken.headerName)) {
            response.setHeader(
                csrfToken.headerName,
                csrfToken.token
            )
        } else {
            response.addHeader(csrfToken.headerName, csrfToken.token)
        }
    }

    override fun loadToken(request: HttpServletRequest): CsrfToken? {
        return request.getAttribute(CsrfToken::class.java.name) as? CsrfToken
    }

    fun clearToken(response: HttpServletResponse) {
        if (response.headerNames.contains(SecurityConsts.X_CSRF_TOKEN)) {
            response.setHeader(SecurityConsts.X_CSRF_TOKEN, "")
        }
    }
}
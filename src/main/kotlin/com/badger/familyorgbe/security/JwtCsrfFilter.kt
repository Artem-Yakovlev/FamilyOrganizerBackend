package com.badger.familyorgbe.security

import com.badger.familyorgbe.infoLog
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.InvalidCsrfTokenException
import org.springframework.security.web.csrf.MissingCsrfTokenException
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtCsrfFilter(
    private val tokenRepository: CsrfTokenRepository,
    private val resolver: HandlerExceptionResolver
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.setAttribute(HttpServletResponse::class.java.name, response)
        var csrfToken = tokenRepository.loadToken(request)
        val missingToken = csrfToken == null

        if (missingToken) {
            csrfToken = tokenRepository.generateToken(request)
            tokenRepository.saveToken(csrfToken, request, response)
        }

        request.apply {
            setAttribute(CsrfToken::class.java.name, csrfToken)
            setAttribute(csrfToken.parameterName, csrfToken)
        }

        if (request.servletPath == "/auth/login") {
            try {
                filterChain.doFilter(request, response)
            } catch (e: Exception) {
                resolver.resolveException(request, response, null, MissingCsrfTokenException(csrfToken.getToken()))
            }
        } else {
            val actualToken = request.getHeader(csrfToken.headerName) ?: request.getParameter(csrfToken.parameterName)
            try {
                if (actualToken.isEmpty()) {
                    Jwts.parser()
                        .setSigningKey(SecurityConsts.JWT_SECRET)
                        .parseClaimsJws(actualToken)
                    filterChain.doFilter(request, response)
                } else {
                    resolver.resolveException(
                        request,
                        response,
                        null,
                        InvalidCsrfTokenException(csrfToken, actualToken)
                    )
                }
            } catch (e: JwtException) {
                if (missingToken) {
                    resolver.resolveException(request, response, null, MissingCsrfTokenException(actualToken))
                } else {
                    resolver.resolveException(
                        request,
                        response,
                        null,
                        InvalidCsrfTokenException(csrfToken, actualToken)
                    )
                }
            }
        }
    }
}
package com.badger.familyorgbe.repository.jwt

import com.badger.familyorgbe.core.exception.InvalidJwtException
import com.badger.familyorgbe.core.second
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.spec.SecretKeySpec


class JwtRepository : IJwtRepository {

    private var secretKeySpec = SecretKeySpec(JWT_SECRET.toByteArray(), SignatureAlgorithm.HS256.jcaName)
    private val validator = DefaultJwtSignatureValidator(SignatureAlgorithm.HS256, secretKeySpec)

    override fun generateToken(email: String): String {
        val id = UUID.randomUUID().toString().removeDashes()
        val now = Date()

        val expMillis = LocalDateTime.now()
            .plusYears(ONE_YEAR)
            .atZone(ZoneId.systemDefault())
            .toInstant()

        return Jwts.builder()
            .setId(id)
            .setIssuedAt(now)
            .setSubject(email)
            .setNotBefore(now)
            .setExpiration(Date.from(expMillis))
            .signWith(SignatureAlgorithm.HS256, JWT_SECRET.toByteArray())
            .compact()
    }

    override fun validateToken(token: String): Boolean {
        val tokenChunks = token.split(DOT)
        return if (tokenChunks.size == TOKEN_CHUNKS_SIZE) {
            val cleanToken = "${tokenChunks.first()}$DOT${tokenChunks.second()}"
            val signature = tokenChunks.last()

            validator.isValid(cleanToken, signature)
        } else {
            false
        }
    }

    override fun getEmail(token: String): String {
        return try {
            Jwts.parser()
                .setSigningKey(JWT_SECRET.toByteArray())
                .parseClaimsJws(token)
                .body.subject
        } catch (e: Exception) {
            throw InvalidJwtException("Invalid token")
        }
    }

    private fun String.removeDashes() = replace(DASH, EMPTY)

    companion object {
        private const val JWT_SECRET = "familyJwtConst"

        private const val ONE_YEAR = 1L

        private const val TOKEN_CHUNKS_SIZE = 3

        private const val DASH = "-"
        private const val EMPTY = ""
        private const val DOT = "."
    }
}
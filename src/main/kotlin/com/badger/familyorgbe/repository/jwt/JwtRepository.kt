package com.badger.familyorgbe.repository.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class JwtRepository : IJwtRepository {

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
            .setNotBefore(now)
            .setExpiration(Date.from(expMillis))
            .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
            .compact()
    }

    override fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJwt(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getEmail(token: String): String? {
        return try {
            Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJwt(token)
                .body.subject
        } catch (e: Exception) {
            null
        }
    }

    private fun String.removeDashes() = replace(DASH, EMPTY)

    companion object {
        private const val JWT_SECRET = "familyJwtConst"

        private const val ONE_YEAR = 1L

        private const val DASH = "-"
        private const val EMPTY = ""
    }
}
package com.teamsparta.todo.infra.jwt

import com.teamsparta.todo.domain.user.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.secret_key}") private val secretKey: String,
    @Value("\${jwt.access_token_expiration_hour}") private val accessTokenExpirationHour: Long,
) {

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun validateToken(token: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }
    }

    fun generateAccessToken(user: User): String {
        return generateToken(user, Duration.ofHours(accessTokenExpirationHour))
    }

    private fun generateToken(user: User, expirationPeriod: Duration?): String {
        val now = Instant.now()

        return Jwts.builder()
            .subject(user.id.toString())
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .signWith(key)
            .compact()
    }

    fun getUserId(token: String): Long {
        return Jwts
            .parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .subject
            .toLong()
    }
}
package org.example.authservice.sercurity;

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import java.util.Date
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(username: String, role: String): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setIssuedAt(Date())
            .setExpiration(getExpiryDate())
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getExpiryDate(): Date {
        val now = Date()
        return Date(now.time + expiration)
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username, "REFRESH")
    }

    fun getUsername(token: String): String =
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
}

package org.example.authservice.service;

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.example.authservice.entity.JwtWhitelistRepository
import org.example.authservice.entity.JwtWhitelist
import java.time.LocalDateTime

@Service
class JwtWhitelistService(private val repository: JwtWhitelistRepository) {

    fun addToken(userId: Long, accessToken: String, refreshToken: String?, expireAt: LocalDateTime): JwtWhitelist {
        val jwt = JwtWhitelist(
            userId = userId,
            accessToken = accessToken,
            refreshToken = refreshToken,
            expireAt = expireAt,
            isExpired = false
        )
        return repository.save(jwt)
    }

    fun isTokenValid(accessToken: String): Boolean {
        val jwtOpt = repository.findByAccessTokenAndIsExpiredFalse(accessToken)
        return jwtOpt.isPresent && jwtOpt.get().expireAt.isAfter(LocalDateTime.now())
    }

    fun getValidTokens(userId: Long): List<JwtWhitelist> {
        return repository.findByUserIdAndIsExpiredFalse(userId)
            .filter { it.expireAt.isAfter(LocalDateTime.now()) }
    }

    @Transactional
    fun revokeToken(accessToken: String) {
        repository.findByAccessTokenAndIsExpiredFalse(accessToken).ifPresent {
            it.isExpired = true;
            repository.save(it)
        }
    }

    @Transactional
    fun cleanupExpiredTokens() {
        val now = LocalDateTime.now()
        val expiredTokens = repository.findAll()
            .filter { it.expireAt.isBefore(now) || it.isExpired }
        repository.deleteAll(expiredTokens)
    }
}

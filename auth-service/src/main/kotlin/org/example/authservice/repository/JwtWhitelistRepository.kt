package org.example.authservice.entity;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.example.authservice.entity.JwtWhitelist
import java.util.*

@Repository
interface JwtWhitelistRepository : JpaRepository<JwtWhitelist, Long> {
    fun findByUserIdAndIsExpiredFalse(userId: Long): List<JwtWhitelist>
    fun findByAccessTokenAndIsExpiredFalse(accessToken: String): Optional<JwtWhitelist>
}

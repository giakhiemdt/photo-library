package org.example.authservice.entity;

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.GenerationType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.Column
import jakarta.persistence.Index
import java.time.LocalDateTime

@Entity
@Table(name = "jwt_whitelist", indexes = [
    Index(name = "idx_user_expired", columnList = "userId, isExpired")
]
)
data class JwtWhitelist(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long, 

    @Column(nullable = false, length = 512, unique = true)
    val accessToken: String, 

    @Column(length = 512, unique = true)
    val refreshToken: String? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val expireAt: LocalDateTime, 

    @Column(nullable = false)
    var isExpired: Boolean = false 
)

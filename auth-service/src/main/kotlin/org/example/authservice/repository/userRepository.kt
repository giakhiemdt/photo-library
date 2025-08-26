package org.example.authservice.repository;

import org.example.authservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByUserId(id: Long): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}

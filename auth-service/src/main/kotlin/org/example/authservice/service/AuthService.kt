package org.example.authservice.service;

import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import org.example.authservice.enum.Role
import org.example.authservice.entity.User
import org.example.authservice.sercurity.JwtTokenProvider
import org.example.authservice.mapper.toResponse
import org.example.authservice.repository.UserRepository
import RegisterRequest
import UserResponse
import LoginRequest
import TokenResponse

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun register(req: RegisterRequest): UserResponse {
        if (userRepository.existsByUsername(req.username)) {
            throw IllegalArgumentException("Username already exists")
        }
        if (userRepository.existsByEmail(req.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val user = User(
            username = req.username,
            email = req.email,
            password = passwordEncoder.encode(req.password),
            role = Role.USER
        )
        val savedUser = userRepository.save(user)
        return savedUser.toResponse()
    }

    fun login(req: LoginRequest): TokenResponse {
        val user = userRepository.findByUsername(req.username)
            ?: throw IllegalArgumentException("Invalid username or password")

        if (!passwordEncoder.matches(req.password, user.password)) {
            throw IllegalArgumentException("Invalid username or password")
        }

        val accessToken = jwtTokenProvider.generateToken(user.username, user.role.name)
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.username)

        return TokenResponse(accessToken, refreshToken)
    }
}

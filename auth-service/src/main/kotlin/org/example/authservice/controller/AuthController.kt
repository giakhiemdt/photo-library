package org.example.authservice.controller;

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.example.authservice.service.AuthService
import RegisterRequest
import LoginRequest
import UserResponse
import TokenResponse
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(authService.register(req))
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(authService.login(req))
    }
    
}

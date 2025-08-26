package org.example.gatewayservice.service;

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AuthClient(private val webClientBuilder: WebClient.Builder) {

    fun checkToken(token: String): Boolean {
        return webClientBuilder.build()
            .get()
            .uri("http://auth-service:8081/auth/check-token?token=$token")
            .retrieve()
            .bodyToMono(Boolean::class.java)
            .block() ?: false
    }
}

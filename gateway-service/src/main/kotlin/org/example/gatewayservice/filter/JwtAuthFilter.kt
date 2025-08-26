package org.example.gatewayservice.filter;

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.http.HttpStatus
import org.springframework.http.HttpHeaders
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.beans.factory.annotation.Value
import reactor.core.publisher.Mono

@Component
class JwtAuthFilter(
    @Value("\${jwt.secret}") private val secret: String,
) : GatewayFilter {

    private val excludedPaths = listOf(
        "/auth/login",
        "/auth/register"
    )

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request

        if (excludedPaths.any { request.uri.path.startsWith(it) }) {
            return chain.filter(exchange)
        }

        val response = exchange.response

        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.statusCode = HttpStatus.UNAUTHORIZED
            return response.setComplete()
        }

        val token = authHeader.substring(7)

        try {

            val userInfo = validateToken(token)

            val mutatedRequest = exchange.request.mutate()
                .header("X-User-Id", userInfo.userId)
                .header("X-User-Role", userInfo.role)
                .build()

            val mutatedExchange = exchange.mutate().request(mutatedRequest).build()
            return chain.filter(mutatedExchange)
        } catch (ex: Exception) {
            response.statusCode = HttpStatus.UNAUTHORIZED
            return response.setComplete()
        }
    }

    private fun validateToken(token: String): UserInfo {
        val claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
        return UserInfo(claims["userId"].toString(), claims["role"].toString())
    }
}

data class UserInfo(val userId: String, val role: String)

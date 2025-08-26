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

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val response = exchange.response

        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.statusCode = HttpStatus.UNAUTHORIZED
            return response.setComplete()
        }

        val token = authHeader.substring(7)

        try {
            // Decode token
            val claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body

            // Optional: gọi Auth Service check whitelist
            // AuthClient.checkToken(token)

            exchange.request.mutate()
                .header("X-User-Id", claims["userId"].toString())
                .header("X-User-Role", claims["role"].toString())
                .build()

        } catch (ex: Exception) {
            response.statusCode = HttpStatus.UNAUTHORIZED
            return response.setComplete()
        }

        return chain.filter(exchange)
    }
}

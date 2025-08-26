package org.example.gatewayservice.config;

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.example.gatewayservice.filter.JwtAuthFilter
import org.springframework.cloud.gateway.route.RouteLocator

@Configuration
class GatewayConfig(
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("auth-service") {
                it.path("/auth/**")
                    .filters { f -> f.filter(jwtAuthFilter) }
                    .uri("http://localhost:8081") 
            }
            .route("auth-docs") {
                it.path("/v3/api-docs/auth")
                    .filters { f -> f.rewritePath("/v3/api-docs/auth", "/v3/api-docs") }
                    .uri("http://localhost:8081")
            }
            .build()
    }
}

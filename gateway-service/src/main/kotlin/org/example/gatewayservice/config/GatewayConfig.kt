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

    // @Bean
    // fun routes(builder: RouteLocatorBuilder): RouteLocator {
    //     return builder.routes()
    //         .route("auth-service") {
    //             it.path("/auth/**")
    //                 .uri("http://localhost:8081") // Service name trong Docker hoặc Kubernetes
    //         }
    //         // .route("media-service") {
    //         //     it.path("/media/**")
    //         //         .filters { f -> f.filter(jwtAuthFilter) } // Áp filter check JWT
    //         //         .uri("http://media-service:8082")
    //         // }
    //         .build()
    // }
}

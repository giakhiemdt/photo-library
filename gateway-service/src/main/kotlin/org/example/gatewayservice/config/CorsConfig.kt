package org.example.gatewayservice.config;

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration

@Configuration
class CorsConfig {
    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val config = CorsConfiguration()
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        
        return CorsWebFilter(source)
    }
}

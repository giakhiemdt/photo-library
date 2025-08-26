package org.example.authservice.config;

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openApiCustomiser(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi: OpenAPI ->
            openApi.servers = listOf(Server().url("http://localhost:8080")) 
        }
    }
}

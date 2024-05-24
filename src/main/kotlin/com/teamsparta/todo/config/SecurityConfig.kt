package com.teamsparta.todo.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .headers { it.frameOptions { frameOptions -> frameOptions.sameOrigin() } }
        .authorizeHttpRequests {
            it.requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/api/**", "/sign-up", "/sign-in")
                .permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()!!

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
package com.teamsparta.todo

import com.teamsparta.todo.infra.auth.AuthArgumentResolver
import com.teamsparta.todo.infra.jwt.JwtPlugin
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val jwtPlugin: JwtPlugin
) : WebMvcConfigurer {
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(AuthArgumentResolver(jwtPlugin))
    }
}
package com.teamsparta.todo.infra.auth

import com.teamsparta.todo.infra.jwt.JwtPlugin
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthArgumentResolver(
    private val jwtPlugin: JwtPlugin
) : HandlerMethodArgumentResolver {
    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java) && parameter.parameterType.isAssignableFrom(
            AuthUser::class.java
        )
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AuthUser {
        val request: HttpServletRequest =
            webRequest.getNativeRequest(HttpServletRequest::class.java) ?: throw IllegalArgumentException()

        val token = request.getBearerToken()

        if (token != null) {
            jwtPlugin.validateToken(token)
                .onSuccess { return AuthUser(id = jwtPlugin.getUserId(token)) }
                .onFailure { throw IllegalArgumentException("Invalid Token") }
        }
        throw IllegalArgumentException("Invalid Token")
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val header = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(header)?.groupValues?.get(1)
    }
}
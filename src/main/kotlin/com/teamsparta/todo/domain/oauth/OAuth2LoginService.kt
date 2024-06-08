package com.teamsparta.todo.domain.oauth

import com.teamsparta.todo.domain.user.service.UserService
import com.teamsparta.todo.infra.jwt.JwtPlugin
import org.springframework.stereotype.Service

@Service
class OAuth2LoginService(
    private val kakaoOAuth2Client: KakaoOAuth2Client,
    private val userService: UserService,
    private val jwtPlugin: JwtPlugin
) {
    fun getOAuth2LoginPage(): String {
        return kakaoOAuth2Client.getOAuth2LoginPage()
    }

    fun login(code: String): String {
        return kakaoOAuth2Client.getAccessToken(code)
            .let { kakaoOAuth2Client.getUserInfo(it) }
            .let { userService.registerIfAbsent(it) }
            .let { jwtPlugin.generateAccessToken(it) }
    }
}

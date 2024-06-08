package com.teamsparta.todo.domain.oauth

import com.teamsparta.todo.domain.oauth.dto.KakaoTokenResponse
import com.teamsparta.todo.domain.oauth.dto.KakaoUserInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoOAuth2Client(
    @Value("\${oauth2.kakao.client_id}") val clientId: String,
    @Value("\${oauth2.kakao.redirect_uri}") val redirectUri: String,
    @Value("\${oauth2.kakao.auth_url}") val authUrl: String,
    @Value("\${oauth2.kakao.api_url}") val apiUrl: String,
    private val restClient: RestClient,
) {

    fun getOAuth2LoginPage(): String {
        return "${authUrl}/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code"
    }

    fun getAccessToken(code: String): String {
        val parameters = mapOf(
            "grant_type" to "authorization_code",
            "code" to code,
            "client_id" to clientId
        )

        return restClient.post()
            .uri("$authUrl/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { setAll(parameters) })
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("카카오 AccessToken 조회 실패")
            }
            .body<KakaoTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("카카오 AccessToken 조회 실패")
    }

    fun getUserInfo(accessToken: String): KakaoUserInfo {
        return restClient.get()
            .uri(apiUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("카카오 UserInfo 조회 실패")
            }
            .body<KakaoUserInfo>()
            ?: throw RuntimeException("카카오 UserInfo 조회 실패")
    }
}

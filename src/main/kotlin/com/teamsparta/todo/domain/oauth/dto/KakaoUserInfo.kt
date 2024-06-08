package com.teamsparta.todo.domain.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.teamsparta.todo.domain.user.model.User
import java.time.LocalDateTime

data class KakaoUserInfo(
    val id: String,
    @JsonProperty("kakao_account")
    val kakaoAccount: KaKaoAccount
)

fun KakaoUserInfo.toEntity(): User {
    return User(
        isSocialMember = true,
        provider = "KAKAO",
        providerId = id,
        name = kakaoAccount.profile.nickname,
        createdAt = LocalDateTime.now()
    )
}

package com.teamsparta.todo.domain.user.service

import com.teamsparta.todo.domain.oauth.dto.KakaoUserInfo
import com.teamsparta.todo.domain.oauth.dto.toEntity
import com.teamsparta.todo.domain.user.dto.LoginResponse
import com.teamsparta.todo.domain.user.dto.SignInRequest
import com.teamsparta.todo.domain.user.dto.SingUpRequest
import com.teamsparta.todo.domain.user.model.User
import com.teamsparta.todo.domain.user.repository.UserRepository
import com.teamsparta.todo.infra.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {

    @Transactional
    fun signUp(request: SingUpRequest): LoginResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("User already exists")
        return userRepository.save(request.toEntity(passwordEncoder))
            .let { LoginResponse(jwtPlugin.generateAccessToken(it)) }
    }

    fun signIn(request: SignInRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw IllegalArgumentException("Wrong Email/PW")
        if (!user.isValidPassword(request.password, passwordEncoder)) throw IllegalArgumentException("Wrong Email/PW")
        return LoginResponse(jwtPlugin.generateAccessToken(user))
    }

    fun registerIfAbsent(userInfo: KakaoUserInfo): User {
        return userRepository.findByProviderAndProviderId("KAKAO", userInfo.id)
            ?: userRepository.save(userInfo.toEntity())
    }
}

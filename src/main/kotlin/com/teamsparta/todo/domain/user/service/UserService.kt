package com.teamsparta.todo.domain.user.service

import com.teamsparta.todo.domain.user.dto.SignInRequest
import com.teamsparta.todo.domain.user.dto.SingUpRequest
import com.teamsparta.todo.domain.user.dto.UserResponse
import com.teamsparta.todo.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signUp(request: SingUpRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("User already exists")
        return userRepository.save(request.toEntity(passwordEncoder)).toResponse()
    }

    fun signIn(request: SignInRequest): UserResponse {
        val user = userRepository.findByEmail(request.email) ?: throw IllegalArgumentException("Wrong Email/PW")
        if (!user.isValidPassword(request.password, passwordEncoder)) throw IllegalArgumentException("Wrong Email/PW")
        return user.toResponse()
    }

}

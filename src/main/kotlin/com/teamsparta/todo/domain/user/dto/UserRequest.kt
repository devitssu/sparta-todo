package com.teamsparta.todo.domain.user.dto

import com.teamsparta.todo.domain.user.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

data class SingUpRequest(

    @field:Email
    val email: String,

    @field:NotBlank
    val name: String,

    @field:Length(min = 4, max = 20)
    val password: String
) {
    fun toEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            email = this.email,
            name = this.name,
            password = passwordEncoder.encode(this.password),
            createdAt = LocalDateTime.now()
        )
    }
}

data class SignInRequest(

    @field:Email
    val email: String,

    @field:Length(min = 4, max = 20)
    val password: String
)

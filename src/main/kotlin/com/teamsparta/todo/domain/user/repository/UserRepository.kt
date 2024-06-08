package com.teamsparta.todo.domain.user.repository

import com.teamsparta.todo.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(name: String): Boolean
    fun findByEmail(email: String): User?
    fun findByProviderAndProviderId(provider: String, providerId: String): User?

}

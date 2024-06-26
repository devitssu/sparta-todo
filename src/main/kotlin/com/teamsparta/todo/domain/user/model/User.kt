package com.teamsparta.todo.domain.user.model

import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.todo.model.ToDo
import com.teamsparta.todo.domain.user.dto.UserResponse
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(

    @Column(nullable = true, unique = true)
    var email: String? = null,

    var name: String,

    @Column(nullable = true)
    var password: String? = null,

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var todos: MutableList<ToDo> = mutableListOf(),

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var comments: MutableList<Comment> = mutableListOf(),

    var createdAt: LocalDateTime,

    var isSocialMember: Boolean,

    @Column(nullable = true)
    var provider: String? = null,

    @Column(nullable = true)
    var providerId: String? = null

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id ?: throw IllegalStateException("User id cannot be null"),
            name = this.name
        )
    }

    fun isValidPassword(rawPassword: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(rawPassword, this.password)
    }
}

package com.teamsparta.todo.domain.comment.dto

import com.teamsparta.todo.domain.user.dto.UserResponse
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: UserResponse,
    val createdAt: LocalDateTime,
    val toDoId: Long
)

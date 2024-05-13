package com.teamsparta.todo.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val toDoId: Long
)

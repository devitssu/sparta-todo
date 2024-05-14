package com.teamsparta.todo.domain.todo.dto

import com.teamsparta.todo.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class ToDoResponse(
    val id: Long,
    val title: String,
    val content: String?,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val status: Boolean,
    val comments: List<CommentResponse>,
)

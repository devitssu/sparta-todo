package com.teamsparta.todo.domain.todo.dto

import java.time.LocalDateTime

data class ToDoResponse(
    val id: Long,
    val title: String,
    val content: String?,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val status: Boolean
)

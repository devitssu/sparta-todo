package com.teamsparta.todo.domain.todo.dto

import com.teamsparta.todo.domain.todo.model.ToDo
import java.time.LocalDateTime

data class CreateToDoRequest (
    val title: String,
    val content: String?,
    val createdBy: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun CreateToDoRequest.toEntity(): ToDo {
    return ToDo(
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
    )
}
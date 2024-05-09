package com.teamsparta.todo.domain.dto

import com.teamsparta.todo.domain.model.ToDo
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
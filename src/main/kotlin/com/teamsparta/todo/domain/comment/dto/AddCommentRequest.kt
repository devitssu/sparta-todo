package com.teamsparta.todo.domain.comment.dto

import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.todo.model.ToDo
import java.time.LocalDateTime

data class AddCommentRequest(
    val content: String,
    val createdBy: String,
    val password: String
)

fun AddCommentRequest.toEntity(todo: ToDo): Comment {
    return Comment(
        content = this.content,
        createdBy = this.createdBy,
        password = this.password,
        toDo = todo,
        createdAt = LocalDateTime.now()
    )
}

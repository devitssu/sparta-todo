package com.teamsparta.todo.domain.comment.dto

import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.todo.model.ToDo
import com.teamsparta.todo.domain.user.model.User
import java.time.LocalDateTime

data class AddCommentRequest(
    val content: String
)

fun AddCommentRequest.toEntity(todo: ToDo, user: User): Comment {
    return Comment(
        content = this.content,
        createdBy = user,
        toDo = todo,
        createdAt = LocalDateTime.now()
    )
}

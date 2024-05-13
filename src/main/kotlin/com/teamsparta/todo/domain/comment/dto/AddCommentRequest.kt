package com.teamsparta.todo.domain.comment.dto

data class AddCommentRequest(
    val content: String,
    val createdBy: String,
    val password: String
)

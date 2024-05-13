package com.teamsparta.todo.domain.comment.dto

data class UpdateCommentRequest(
    val content: String,
    val createdBy: String,
    val password: String
)

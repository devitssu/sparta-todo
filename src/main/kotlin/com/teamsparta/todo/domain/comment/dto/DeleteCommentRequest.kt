package com.teamsparta.todo.domain.comment.dto

data class DeleteCommentRequest(
    val createdBy: String,
    val password: String
)

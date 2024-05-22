package com.teamsparta.todo.domain.todo.dto

data class SliceResponse<T>(
    val content: List<T>,
    val nextCursor: Long,
)

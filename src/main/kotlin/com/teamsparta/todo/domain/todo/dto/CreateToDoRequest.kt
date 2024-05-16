package com.teamsparta.todo.domain.todo.dto

import com.teamsparta.todo.domain.todo.model.ToDo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateToDoRequest (
    @field:NotBlank(message = "제목은 공백일 수 없습니다.")
    @field:Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    val title: String,

    @field:NotBlank(message = "내용은 공백일 수 없습니다.")
    @field:Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
    val content: String,

    val createdBy: String,
)

fun CreateToDoRequest.toEntity(): ToDo {
    return ToDo(
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = LocalDateTime.now(),
    )
}
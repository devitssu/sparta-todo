package com.teamsparta.todo.domain.todo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateToDoRequest (
    @field:NotBlank(message = "제목은 공백일 수 없습니다.")
    @field:Size(max = 200, message = "제목은 200자를 초과할 수 없습니다.")
    var title: String,

    @field:NotBlank(message = "내용은 공백일 수 없습니다.")
    @field:Size(max = 1000, message = "내용은 1000자를 초과할 수 없습니다.")
    var content: String,

    var createdBy: String
)

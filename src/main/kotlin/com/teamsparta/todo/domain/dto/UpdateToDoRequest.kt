package com.teamsparta.todo.domain.dto

data class UpdateToDoRequest (
    var title: String,
    var content: String,
    var createdBy: String
)

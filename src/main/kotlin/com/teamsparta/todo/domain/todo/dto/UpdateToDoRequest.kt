package com.teamsparta.todo.domain.todo.dto

data class UpdateToDoRequest (
    var title: String,
    var content: String,
    var createdBy: String
)

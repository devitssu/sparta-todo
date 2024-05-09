package com.teamsparta.todo.domain.service

import com.teamsparta.todo.domain.dto.CreateToDoRequest
import com.teamsparta.todo.domain.dto.ToDoResponse

interface ToDoService {
    fun createToDo(request: CreateToDoRequest): ToDoResponse
}
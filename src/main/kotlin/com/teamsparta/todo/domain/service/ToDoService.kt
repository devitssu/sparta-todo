package com.teamsparta.todo.domain.service

import com.teamsparta.todo.domain.dto.CreateToDoRequest
import com.teamsparta.todo.domain.dto.ToDoResponse
import com.teamsparta.todo.domain.dto.UpdateToDoRequest

interface ToDoService {
    fun createToDo(request: CreateToDoRequest): ToDoResponse
    fun getAllToDos(): List<ToDoResponse>
    fun getToDoById(id: Long): ToDoResponse
    fun deleteToDo(todoId: Long)
    fun updateToDo(toDoId: Long, request: UpdateToDoRequest): ToDoResponse?
}
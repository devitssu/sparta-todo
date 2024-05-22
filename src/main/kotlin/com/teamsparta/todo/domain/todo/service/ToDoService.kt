package com.teamsparta.todo.domain.todo.service

import com.teamsparta.todo.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todo.domain.todo.dto.SliceResponse
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.UpdateToDoRequest

interface ToDoService {
    fun createToDo(request: CreateToDoRequest): ToDoResponse
    fun getAllToDos(sort: String, cursor: Long): SliceResponse<ToDoResponse>
    fun getToDoById(id: Long): ToDoResponse
    fun deleteToDo(todoId: Long)
    fun updateToDo(toDoId: Long, request: UpdateToDoRequest): ToDoResponse
    fun updateToDoStatus(toDoId: Long, status: Boolean): ToDoResponse
    fun getFilteredToDos(sort: String, keyword: String, cursor: Long): SliceResponse<ToDoResponse>
}
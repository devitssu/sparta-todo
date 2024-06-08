package com.teamsparta.todo.domain.todo.service

import com.teamsparta.todo.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todo.domain.todo.dto.SliceResponse
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todo.infra.auth.AuthUser

interface ToDoService {
    fun createToDo(loginUser: AuthUser, request: CreateToDoRequest): ToDoResponse
    fun getAllToDos(sort: String, cursor: Long): SliceResponse<ToDoResponse>
    fun getToDoById(id: Long): ToDoResponse
    fun deleteToDo(todoId: Long, loginUser: AuthUser)
    fun updateToDo(toDoId: Long, loginUser: AuthUser, request: UpdateToDoRequest): ToDoResponse
    fun updateToDoStatus(toDoId: Long, loginUser: AuthUser, status: Boolean): ToDoResponse
    fun getFilteredToDos(sort: String, keyword: String, cursor: Long): SliceResponse<ToDoResponse>
}
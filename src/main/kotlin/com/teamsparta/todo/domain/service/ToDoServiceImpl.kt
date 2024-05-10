package com.teamsparta.todo.domain.service

import com.teamsparta.todo.domain.dto.CreateToDoRequest
import com.teamsparta.todo.domain.dto.ToDoResponse
import com.teamsparta.todo.domain.dto.toEntity
import com.teamsparta.todo.domain.model.toResponse
import com.teamsparta.todo.domain.repository.ToDoRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ToDoServiceImpl(
    private val toDoRepository: ToDoRepository
) : ToDoService {

    override fun createToDo(request: CreateToDoRequest): ToDoResponse {
        return toDoRepository.save(request.toEntity()).toResponse()
    }

    override fun getAllToDos(): List<ToDoResponse> {
        return toDoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).map { it.toResponse() }
    }

    override fun getToDoById(id: Long): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("ToDo", id)
        return todo.toResponse()
    }

    override fun deleteToDo(todoId: Long) {
        val todo = toDoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("ToDo", todoId)
        toDoRepository.delete(todo)
    }
}
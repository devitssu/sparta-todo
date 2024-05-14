package com.teamsparta.todo.domain.todo.service

import com.teamsparta.todo.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todo.domain.todo.dto.toEntity
import com.teamsparta.todo.domain.todo.model.toResponse
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
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

    override fun getAllToDos(sort: String): List<ToDoResponse> {
        return toDoRepository.findAll(Sort.by(getDirection(sort), "createdAt")).map { it.toResponse() }
    }

    override fun getFilteredToDos(sort: String, keyword: String): List<ToDoResponse> {
        return toDoRepository.findByCreatedBy(keyword, Sort.by(getDirection(sort), "createdAt")).map { it.toResponse() }
    }

    override fun getToDoById(id: Long): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("ToDo", id)
        return todo.toResponse()
    }

    override fun deleteToDo(todoId: Long) {
        val todo = toDoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("ToDo", todoId)
        toDoRepository.delete(todo)
    }

    override fun updateToDo(toDoId: Long, request: UpdateToDoRequest): ToDoResponse? {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        todo.title = request.title
        todo.content = request.content
        todo.createdBy = request.createdBy

        return toDoRepository.save(todo).toResponse()
    }

    override fun updateToDoStatus(toDoId: Long, status: Boolean): ToDoResponse? {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        todo.status = status
        return toDoRepository.save(todo).toResponse()
    }

    private fun getDirection(sort: String) = when (sort) {
        "asc" -> Sort.Direction.ASC
        else -> Sort.Direction.DESC
    }
}
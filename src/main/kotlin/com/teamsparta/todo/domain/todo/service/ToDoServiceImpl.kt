package com.teamsparta.todo.domain.todo.service

import com.teamsparta.todo.domain.todo.dto.*
import com.teamsparta.todo.domain.todo.model.toResponse
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ToDoServiceImpl(
    private val toDoRepository: ToDoRepository
) : ToDoService {

    private val pageSize = 5

    override fun createToDo(request: CreateToDoRequest): ToDoResponse {
        return toDoRepository.save(request.toEntity()).toResponse()
    }

    override fun getAllToDos(sort: String, cursor: Long): SliceResponse<ToDoResponse> {
        val direction = getDirection(sort)
        val pageable: Pageable = PageRequest.of(0, pageSize, direction, "id")

        val slice = if (cursor == 0L) {
            toDoRepository.findSliceBy(pageable)
        } else {
            when (direction) {
                Sort.Direction.ASC -> toDoRepository.findSliceByIdGreaterThan(cursor, pageable)
                Sort.Direction.DESC -> toDoRepository.findSliceByIdLessThan(cursor, pageable)
            }
        }

        val nextCursor =
            if (slice.isLast) -1 else slice.content.last().id ?: throw IllegalStateException("Todo ID is null")

        return SliceResponse<ToDoResponse>(slice.content.map { it.toResponse() }.toList(), nextCursor)
    }

    override fun getFilteredToDos(sort: String, keyword: String, cursor: Long): SliceResponse<ToDoResponse> {
        val direction = getDirection(sort)
        val pageable: Pageable = PageRequest.of(0, pageSize, direction, "id")

        val slice = if (cursor == 0L) {
            toDoRepository.findByCreatedBy(keyword, pageable)
        } else {
            when (direction) {
                Sort.Direction.ASC -> toDoRepository.findByCreatedByAndIdGreaterThan(keyword, cursor, pageable)
                Sort.Direction.DESC -> toDoRepository.findByCreatedByAndIdLessThan(keyword, cursor, pageable)
            }
        }

        val nextCursor =
            if (slice.isLast) -1 else slice.content.last().id ?: throw IllegalStateException("Todo ID is null")

        return SliceResponse<ToDoResponse>(slice.content.map { it.toResponse() }.toList(), nextCursor)
    }

    override fun getToDoById(id: Long): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("ToDo", id)
        return todo.toResponse()
    }

    override fun deleteToDo(todoId: Long) {
        val todo = toDoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("ToDo", todoId)
        toDoRepository.delete(todo)
    }

    override fun updateToDo(toDoId: Long, request: UpdateToDoRequest): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        todo.title = request.title
        todo.content = request.content
        todo.createdBy = request.createdBy

        return toDoRepository.save(todo).toResponse()
    }

    override fun updateToDoStatus(toDoId: Long, status: Boolean): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        todo.status = status
        return toDoRepository.save(todo).toResponse()
    }

    private fun getDirection(sort: String) = when (sort) {
        "asc" -> Sort.Direction.ASC
        else -> Sort.Direction.DESC
    }
}
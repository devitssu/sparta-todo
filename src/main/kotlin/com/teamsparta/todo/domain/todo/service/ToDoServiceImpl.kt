package com.teamsparta.todo.domain.todo.service

import com.teamsparta.todo.domain.todo.dto.*
import com.teamsparta.todo.domain.todo.model.ToDo
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.domain.user.repository.UserRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import com.teamsparta.todo.exception.UnauthorizedException
import com.teamsparta.todo.infra.auth.AuthUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ToDoServiceImpl(
    private val toDoRepository: ToDoRepository,
    private val userRepository: UserRepository
) : ToDoService {

    private val pageSize = 5

    @Transactional
    override fun createToDo(loginUser: AuthUser, request: CreateToDoRequest): ToDoResponse {
        val user = userRepository.findByIdOrNull(loginUser.id) ?: throw ModelNotFoundException("user", loginUser.id)
        return toDoRepository.save(request.toEntity(user)).toResponse()
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

        return SliceResponse(slice.content.map { it.toResponse() }.toList(), nextCursor)
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

        return SliceResponse(slice.content.map { it.toResponse() }.toList(), nextCursor)
    }

    override fun getToDoById(id: Long): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(id) ?: throw ModelNotFoundException("ToDo", id)
        return todo.toResponse()
    }

    @Transactional
    override fun deleteToDo(todoId: Long, loginUser: AuthUser) {
        val todo = toDoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("ToDo", todoId)
        checkAuthorization(todo, loginUser)
        toDoRepository.delete(todo)
    }

    @Transactional
    override fun updateToDo(toDoId: Long, loginUser: AuthUser, request: UpdateToDoRequest): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        checkAuthorization(todo, loginUser)
        todo.update(request)
        return todo.toResponse()
    }

    @Transactional
    override fun updateToDoStatus(toDoId: Long, loginUser: AuthUser, status: Boolean): ToDoResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        checkAuthorization(todo, loginUser)
        todo.changeStatus(status)
        return todo.toResponse()
    }

    private fun getDirection(sort: String) = when (sort) {
        "asc" -> Sort.Direction.ASC
        else -> Sort.Direction.DESC
    }

    private fun checkAuthorization(todo: ToDo, loginUser: AuthUser) {
        val user = userRepository.findByIdOrNull(loginUser.id) ?: throw ModelNotFoundException("User", loginUser.id)
        if (user.id != todo.createdBy.id) throw UnauthorizedException("권한이 없습니다.")
    }
}
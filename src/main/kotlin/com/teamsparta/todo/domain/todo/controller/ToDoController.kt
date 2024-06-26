package com.teamsparta.todo.domain.todo.controller

import com.teamsparta.todo.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todo.domain.todo.service.ToDoService
import com.teamsparta.todo.infra.auth.AuthUser
import com.teamsparta.todo.infra.auth.LoginUser
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/todos")
@RestController
class ToDoController(
    private val toDoService: ToDoService
) {

    @GetMapping
    fun getAllToDos(
        @RequestParam(required = false, defaultValue = "desc") sort: String,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false, defaultValue = "0") cursor: Long
    ): ResponseEntity<Any> {

        val body = if (keyword != null) {
            toDoService.getFilteredToDos(sort, keyword, cursor)
        } else {
            toDoService.getAllToDos(sort, cursor)
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(body)
    }

    @GetMapping("/{toDoId}")
    fun getToDo(@PathVariable toDoId: Long): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.getToDoById(toDoId))
    }

    @PostMapping
    fun createToDo(
        @LoginUser @Parameter(hidden = true) loginUser: AuthUser,
        @Valid @RequestBody request: CreateToDoRequest
    ): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toDoService.createToDo(loginUser, request))
    }

    @PutMapping("/{toDoId}")
    fun updateToDo(
        @PathVariable toDoId: Long,
        @LoginUser loginUser: AuthUser,
        @Valid @RequestBody request: UpdateToDoRequest
    ): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.updateToDo(toDoId, loginUser, request))
    }

    @PatchMapping("/{toDoId}")
    fun updateToDoStatus(
        @PathVariable toDoId: Long,
        @LoginUser loginUser: AuthUser,
        @RequestParam("status") status: Boolean
    ): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.updateToDoStatus(toDoId, loginUser, status))
    }

    @DeleteMapping("/{todoId}")
    fun deleteToDo(
        @PathVariable todoId: Long,
        @LoginUser loginUser: AuthUser
    ): ResponseEntity<Void> {
        toDoService.deleteToDo(todoId, loginUser)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
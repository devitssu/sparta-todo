package com.teamsparta.todo.domain.todo.controller

import com.teamsparta.todo.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todo.domain.todo.service.ToDoService
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
        @RequestParam(required = false) keyword: String?
    ): ResponseEntity<List<ToDoResponse>> {

        val body = if (keyword != null) {
            toDoService.getFilteredToDos(sort, keyword)
        } else {
            toDoService.getAllToDos(sort)
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
    fun createToDo(@Valid @RequestBody request: CreateToDoRequest): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toDoService.createToDo(request))
    }

    @PutMapping("/{toDoId}")
    fun updateToDo(
        @PathVariable toDoId: Long,
        @Valid @RequestBody request: UpdateToDoRequest
    ): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.updateToDo(toDoId, request))
    }

    @PatchMapping("/{toDoId}")
    fun updateToDoStatus(
        @PathVariable toDoId: Long,
        @RequestParam("status") status: Boolean
    ): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.updateToDoStatus(toDoId, status))
    }

    @DeleteMapping("/{todoId}")
    fun deleteToDo(@PathVariable todoId: Long): ResponseEntity<Void> {
        toDoService.deleteToDo(todoId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }


}
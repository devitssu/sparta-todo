package com.teamsparta.todo.domain.controller

import com.teamsparta.todo.domain.dto.CreateToDoRequest
import com.teamsparta.todo.domain.dto.ToDoResponse
import com.teamsparta.todo.domain.service.ToDoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/todos")
@RestController
class ToDoController(
    private val toDoService: ToDoService
) {

    @GetMapping
    fun getAllToDos(): ResponseEntity<List<ToDoResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.getAllToDos())
    }

    @GetMapping("/{toDoId}")
    fun getToDo(@PathVariable toDoId: Long): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(toDoService.getToDoById(toDoId))
    }

    @PostMapping
    fun createToDo(@RequestBody request: CreateToDoRequest): ResponseEntity<ToDoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(toDoService.createToDo(request))
    }

    @DeleteMapping("/{todoId}")
    fun deleteToDo(@PathVariable todoId: Long): ResponseEntity<Void> {
        toDoService.deleteToDo(todoId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
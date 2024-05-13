package com.teamsparta.todo.domain.todo.repository

import com.teamsparta.todo.domain.todo.model.ToDo
import org.springframework.data.jpa.repository.JpaRepository

interface ToDoRepository: JpaRepository<ToDo, Long> {
}
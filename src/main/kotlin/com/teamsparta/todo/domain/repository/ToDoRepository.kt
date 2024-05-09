package com.teamsparta.todo.domain.repository

import com.teamsparta.todo.domain.model.ToDo
import org.springframework.data.jpa.repository.JpaRepository

interface ToDoRepository: JpaRepository<ToDo, Long> {
}
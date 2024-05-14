package com.teamsparta.todo.domain.todo.repository

import com.teamsparta.todo.domain.todo.model.ToDo
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface ToDoRepository: JpaRepository<ToDo, Long> {
    fun findByCreatedBy(keyword: String, direction: Sort): List<ToDo>
}
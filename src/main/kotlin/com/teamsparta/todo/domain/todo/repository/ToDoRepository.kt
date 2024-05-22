package com.teamsparta.todo.domain.todo.repository

import com.teamsparta.todo.domain.todo.model.ToDo
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface ToDoRepository : JpaRepository<ToDo, Long> {
    fun findByCreatedBy(keyword: String, pageable: Pageable): Slice<ToDo>
    fun findSliceBy(pageable: Pageable): Slice<ToDo>
    fun findSliceByIdLessThan(cursor: Long, pageable: Pageable): Slice<ToDo>
    fun findSliceByIdGreaterThan(cursor: Long, pageable: Pageable): Slice<ToDo>
    fun findByCreatedByAndIdGreaterThan(keyword: String, cursor: Long, pageable: Pageable): Slice<ToDo>
    fun findByCreatedByAndIdLessThan(keyword: String, cursor: Long, pageable: Pageable): Slice<ToDo>
}
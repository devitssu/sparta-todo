package com.teamsparta.todo.domain.model

import com.teamsparta.todo.domain.dto.ToDoResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class ToDo(
    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "createdBy")
    var createdBy: String,

    @Column(name = "createdAt")
    var createdAt: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun ToDo.toResponse(): ToDoResponse {
    return ToDoResponse(
        id = id!!,
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
    )
}
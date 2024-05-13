package com.teamsparta.todo.domain.comment.model

import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.todo.model.ToDo
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Comment(
    @Column(name = "content")
    var content: String,

    @Column(name = "createdBy")
    var createdBy: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "createdAt")
    var createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val toDo: ToDo,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id!!,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        toDoId = this.toDo.id!!
    )
}

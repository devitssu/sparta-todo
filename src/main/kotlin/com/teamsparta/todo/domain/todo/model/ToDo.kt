package com.teamsparta.todo.domain.todo.model

import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ToDo(
    var title: String,
    var content: String,
    var createdBy: String,
    var createdAt: LocalDateTime,
    var status: Boolean = false,

    @OneToMany(mappedBy = "toDo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var comments: MutableList<Comment> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun addComment(comment: Comment) {
        this.comments.add(comment)
    }
}

fun ToDo.toResponse(): ToDoResponse {
    return ToDoResponse(
        id = id!!,
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        status = this.status,
        comments = this.comments.map { it.toResponse() }
    )
}
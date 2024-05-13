package com.teamsparta.todo.domain.todo.model

import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import jakarta.persistence.*
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

    @Column(name = "status")
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
        status = this.status
    )
}
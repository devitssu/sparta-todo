package com.teamsparta.todo.domain.comment.model

import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todo.domain.todo.model.ToDo
import com.teamsparta.todo.domain.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Comment(
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var createdBy: User,
    var createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val toDo: ToDo,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun update(request: UpdateCommentRequest) {
        this.content = request.content
    }
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id ?: throw IllegalStateException("Comment ID is null"),
        content = this.content,
        createdBy = this.createdBy.toResponse(),
        createdAt = this.createdAt,
        toDoId = this.toDo.id!!
    )
}

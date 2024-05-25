package com.teamsparta.todo.domain.comment.model

import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todo.domain.todo.model.ToDo
import com.teamsparta.todo.exception.UnauthorizedException
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Comment(
    var content: String,
    var createdBy: String,
    var password: String,
    var createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val toDo: ToDo,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun isValidToModify(createdBy: String, password: String): Boolean {
        return this.createdBy == createdBy && this.password == password
    }

    fun update(request: UpdateCommentRequest) {
        if (isValidToModify(request.createdBy, request.password)) {
            this.content = request.content
        } else throw UnauthorizedException("이름/비밀번호가 일치하지 않습니다.")
    }
}

fun Comment.toResponse(): CommentResponse {
    return CommentResponse(
        id = id ?: throw IllegalStateException("Comment ID is null"),
        content = this.content,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        toDoId = this.toDo.id!!
    )
}

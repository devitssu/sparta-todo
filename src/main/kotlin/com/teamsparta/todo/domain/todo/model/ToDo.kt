package com.teamsparta.todo.domain.todo.model

import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.UpdateToDoRequest
import com.teamsparta.todo.domain.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import java.time.LocalDateTime

@Entity
class ToDo(
    var title: String,
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var createdBy: User,

    var createdAt: LocalDateTime,
    private var status: Boolean = false,

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "toDo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var comments: MutableList<Comment> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun addComment(comment: Comment) {
        this.comments.add(comment)
    }

    fun changeStatus(status: Boolean) {
        this.status = status
    }

    fun toResponse(): ToDoResponse {
        return ToDoResponse(
            id = id ?: throw IllegalStateException("Todo ID is null"),
            title = this.title,
            content = this.content,
            createdBy = this.createdBy.toResponse(),
            createdAt = this.createdAt,
            status = this.status,
            comments = this.comments.map { it.toResponse() }
        )
    }

    fun update(request: UpdateToDoRequest) {
        this.title = request.title
        this.content = request.content
    }
}


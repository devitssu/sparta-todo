package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.comment.repository.CommentRepository
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentServiceImpl(
    private val commentRepository : CommentRepository,
    private val toDoRepository: ToDoRepository
): CommentService {
    override fun addComment(toDoId: Long, request: AddCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        val comment = Comment(
            content = request.content,
            createdBy = request.createdBy,
            password = request.password,
            toDo = todo,
            createdAt = LocalDateTime.now()
        )

        todo.addComment(comment)

        return commentRepository.save(comment).toResponse()
    }
}


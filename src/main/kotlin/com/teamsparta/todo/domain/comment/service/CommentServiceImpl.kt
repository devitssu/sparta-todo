package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.DeleteCommentRequest
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.comment.repository.CommentRepository
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import com.teamsparta.todo.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.Unauthorized
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

    override fun updateComment(toDoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if(isValid(comment, request.createdBy, request.password)) {
            comment.content = request.content
            return commentRepository.save(comment).toResponse()
        } else throw UnauthorizedException("수정할 수 있는 권한이 없습니다.")
    }

    override fun deleteComment(toDoId: Long, commentId: Long, request: DeleteCommentRequest) {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if(isValid(comment, request.createdBy, request.password)) {
            commentRepository.delete(comment)
        } else throw UnauthorizedException("삭제할 수 있는 권한이 없습니다.")
    }

    private fun isValid(comment: Comment, createdBy: String, password: String): Boolean {
        return comment.createdBy == createdBy && comment.password == password
    }
}


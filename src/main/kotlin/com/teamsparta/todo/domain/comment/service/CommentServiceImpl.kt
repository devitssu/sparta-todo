package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.GeneralResponse
import com.teamsparta.todo.domain.comment.dto.*
import com.teamsparta.todo.domain.comment.model.Comment
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.comment.repository.CommentRepository
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import com.teamsparta.todo.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val toDoRepository: ToDoRepository
) : CommentService {
    override fun addComment(toDoId: Long, request: AddCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        val comment = request.toEntity(todo)
        todo.addComment(comment)

        return commentRepository.save(comment).toResponse()
    }

    override fun updateComment(toDoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (isValid(comment, request.createdBy, request.password)) {
            comment.content = request.content
            return commentRepository.save(comment).toResponse()
        } else throw UnauthorizedException("수정할 수 있는 권한이 없습니다.")
    }

    override fun deleteComment(toDoId: Long, commentId: Long, request: DeleteCommentRequest): GeneralResponse {
        toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (isValid(comment, request.createdBy, request.password)) {
            commentRepository.delete(comment)
            return GeneralResponse(
                HttpStatus.NO_CONTENT.value(),
                "댓글이 삭제되었습니다."
            )
        } else throw UnauthorizedException("삭제할 수 있는 권한이 없습니다.")
    }

    private fun isValid(comment: Comment, createdBy: String, password: String): Boolean {
        return comment.createdBy == createdBy && comment.password == password
    }
}


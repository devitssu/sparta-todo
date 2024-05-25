package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.GeneralResponse
import com.teamsparta.todo.domain.comment.dto.*
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.comment.repository.CommentRepository
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val toDoRepository: ToDoRepository
) : CommentService {
    @Transactional
    override fun addComment(toDoId: Long, request: AddCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)

        val comment = request.toEntity(todo)
        todo.addComment(comment)

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun updateComment(toDoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        comment.update(request)
        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(toDoId: Long, commentId: Long, request: DeleteCommentRequest): GeneralResponse? {
        toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.isValidToModify(request.createdBy, request.password)) {
            commentRepository.delete(comment)
            return GeneralResponse(
                HttpStatus.NO_CONTENT.value(),
                "댓글이 삭제되었습니다."
            )
        }
        return null
    }
}


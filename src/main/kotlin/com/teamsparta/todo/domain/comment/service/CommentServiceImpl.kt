package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.GeneralResponse
import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todo.domain.comment.dto.toEntity
import com.teamsparta.todo.domain.comment.model.toResponse
import com.teamsparta.todo.domain.comment.repository.CommentRepository
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.domain.user.repository.UserRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import com.teamsparta.todo.exception.UnauthorizedException
import com.teamsparta.todo.infra.auth.AuthUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val toDoRepository: ToDoRepository,
    private val userRepository: UserRepository,
) : CommentService {
    @Transactional
    override fun addComment(toDoId: Long, loginUser: AuthUser, request: AddCommentRequest): CommentResponse {
        val todo = toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val user = userRepository.findByIdOrNull(loginUser.id) ?: throw ModelNotFoundException("User", loginUser.id)
        val comment = request.toEntity(todo, user)

        todo.addComment(comment)
        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun updateComment(
        toDoId: Long,
        commentId: Long,
        loginUser: AuthUser,
        request: UpdateCommentRequest
    ): CommentResponse {
        toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if (comment.createdBy.id != loginUser.id) throw UnauthorizedException("권한이 없습니다.")

        comment.update(request)
        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(toDoId: Long, commentId: Long, loginUser: AuthUser): GeneralResponse? {
        toDoRepository.findByIdOrNull(toDoId) ?: throw ModelNotFoundException("ToDo", toDoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if (comment.createdBy.id != loginUser.id) {
            throw UnauthorizedException("권한이 없습니다.")
        }

        commentRepository.delete(comment)
        return GeneralResponse(
            HttpStatus.NO_CONTENT.value(),
            "댓글이 삭제되었습니다."
        )
    }
}


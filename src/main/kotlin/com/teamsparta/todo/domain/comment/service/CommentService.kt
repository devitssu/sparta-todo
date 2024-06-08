package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.GeneralResponse
import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todo.infra.auth.AuthUser

interface CommentService {
    fun addComment(toDoId: Long, loginUser: AuthUser, request: AddCommentRequest): CommentResponse
    fun updateComment(
        toDoId: Long,
        commentId: Long,
        loginUser: AuthUser,
        request: UpdateCommentRequest
    ): CommentResponse

    fun deleteComment(toDoId: Long, commentId: Long, loginUser: AuthUser): GeneralResponse?

}

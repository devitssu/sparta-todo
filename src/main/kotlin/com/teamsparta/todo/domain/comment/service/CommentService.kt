package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.GeneralResponse
import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.DeleteCommentRequest
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest

interface CommentService {
    fun addComment(toDoId: Long, request: AddCommentRequest): CommentResponse
    fun updateComment(toDoId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse
    fun deleteComment(toDoId: Long, commentId: Long, request: DeleteCommentRequest): GeneralResponse

}

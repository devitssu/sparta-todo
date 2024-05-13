package com.teamsparta.todo.domain.comment.service

import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse

interface CommentService {
    fun addComment(toDoId: Long, request: AddCommentRequest): CommentResponse

}

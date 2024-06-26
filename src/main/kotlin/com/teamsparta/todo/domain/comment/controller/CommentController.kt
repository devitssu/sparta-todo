package com.teamsparta.todo.domain.comment.controller

import com.teamsparta.todo.GeneralResponse
import com.teamsparta.todo.domain.comment.dto.AddCommentRequest
import com.teamsparta.todo.domain.comment.dto.CommentResponse
import com.teamsparta.todo.domain.comment.dto.UpdateCommentRequest
import com.teamsparta.todo.domain.comment.service.CommentService
import com.teamsparta.todo.infra.auth.AuthUser
import com.teamsparta.todo.infra.auth.LoginUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/todos/{toDoId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun addComment(
        @PathVariable toDoId: Long,
        @LoginUser loginUser: AuthUser,
        @RequestBody request: AddCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.addComment(toDoId, loginUser, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable toDoId: Long,
        @PathVariable commentId: Long,
        @LoginUser loginUser: AuthUser,
        @RequestBody request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(toDoId, commentId, loginUser, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable toDoId: Long,
        @PathVariable commentId: Long,
        @LoginUser loginUser: AuthUser
    ): ResponseEntity<GeneralResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.deleteComment(toDoId, commentId, loginUser))
    }
}
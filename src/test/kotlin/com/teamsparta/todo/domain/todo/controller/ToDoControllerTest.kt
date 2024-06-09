package com.teamsparta.todo.domain.todo.controller

import com.ninjasquad.springmockk.MockkBean
import com.teamsparta.todo.domain.todo.model.ToDo
import com.teamsparta.todo.domain.todo.service.ToDoService
import com.teamsparta.todo.domain.user.model.User
import com.teamsparta.todo.exception.UnauthorizedException
import com.teamsparta.todo.infra.jwt.JwtPlugin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class ToDoControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val jwtPlugin: JwtPlugin,
    @MockkBean val todoService: ToDoService
) : DescribeSpec({

    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    val user = User(
        name = "writer",
        isSocialMember = false,
        createdAt = LocalDateTime.now()
    )
    user.id = 1L

    val todo = ToDo(
        title = "title",
        content = "content",
        createdBy = user,
        createdAt = LocalDateTime.now(),
        status = false,
        comments = mutableListOf()
    )
    todo.id = 1L

    describe("PUT /api/todos/{toDoId}") {
        context("해당 할일의 작성자가 맞으면") {
            it("status code 200을 반환한다.") {
                every { todoService.updateToDo(any(), any(), any()) } returns todo.toResponse()

                val token = jwtPlugin.generateAccessToken(user = user)

                val result = mockMvc.perform(
                    put("/api/todos/1")
                        .header("Authorization", "Bearer $token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            """
                                {
                                  "title": "updated title",
                                  "content": "updated content"
                                }
                            """.trimIndent()
                        )
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe HttpStatus.OK.value()
            }
        }

        context("해당 할일의 작성자가 아니면") {
            it("status code 401을 반환한다.") {
                every { todoService.updateToDo(any(), any(), any()) } throws UnauthorizedException("권한이 없습니다.")

                val token = jwtPlugin.generateAccessToken(user = user)

                val result = mockMvc.perform(
                    put("/api/todos/1")
                        .header("Authorization", "Bearer $token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            """
                                {
                                  "title": "updated title",
                                  "content": "updated content"
                                }
                            """.trimIndent()
                        )
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        context("로그인 한 사용자가 아니면") {
            it("status code 403을 반환한다.") {
                val result = mockMvc.perform(
                    put("/api/todos/1")
                        .header("Authorization", "Bearer ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            """
                                {
                                  "title": "updated title",
                                  "content": "updated content"
                                }
                            """.trimIndent()
                        )
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }
    }


})

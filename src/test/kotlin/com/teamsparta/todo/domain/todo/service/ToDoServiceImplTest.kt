package com.teamsparta.todo.domain.todo.service

import com.teamsparta.todo.domain.todo.dto.CreateToDoRequest
import com.teamsparta.todo.domain.todo.dto.ToDoResponse
import com.teamsparta.todo.domain.todo.dto.toEntity
import com.teamsparta.todo.domain.todo.repository.ToDoRepository
import com.teamsparta.todo.domain.user.model.User
import com.teamsparta.todo.domain.user.repository.UserRepository
import com.teamsparta.todo.exception.ModelNotFoundException
import com.teamsparta.todo.infra.auth.AuthUser
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

class ToDoServiceImplTest : BehaviorSpec({
    val toDoRepository: ToDoRepository = mockk()
    val userRepository: UserRepository = mockk()

    val toDoService = ToDoServiceImpl(toDoRepository, userRepository)

    afterContainer {
        clearAllMocks()
    }

    given("새로운 할일을 생성할 때") {
        val toDoRequest = CreateToDoRequest(
            title = "title",
            content = "content",
        )

        `when`("작성자가 존재하지 않는 회원이면") {
            val nonExistUser = AuthUser(id = 1L)

            every { userRepository.findByIdOrNull(any()) } returns null

            then("ModelNotFoundException이 발생한다.") {
                shouldThrow<ModelNotFoundException> {
                    toDoService.createToDo(nonExistUser, toDoRequest)
                }
            }
        }

        `when`("작성자가 존재하는 회원이면") {
            val existUser = AuthUser(id = 1L)

            val user = User(name = "user", isSocialMember = false, createdAt = LocalDateTime.now())
            user.id = existUser.id

            then("ToDoResponse를 반환한다.") {
                val newTodo = toDoRequest.toEntity(user)
                newTodo.id = 1L

                every { userRepository.findByIdOrNull(any()) } returns user
                every { toDoRepository.save(any()) } returns newTodo

                val result = toDoService.createToDo(existUser, toDoRequest)

                result.shouldBeTypeOf<ToDoResponse>()
                result.title shouldBe toDoRequest.title
                result.content shouldBe toDoRequest.content
                result.createdBy.id shouldBe existUser.id
            }
        }
    }
})

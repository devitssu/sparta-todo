package com.teamsparta.todo.domain.user.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class UserTest : BehaviorSpec({
    given("암호화된 비밀번호를 가진 user와 비밀번호를 검증할때") {
        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

        val originPassword = "origin1234!"
        val encodedPassword = passwordEncoder.encode(originPassword)

        val user = User(
            name = "test",
            email = "test@test.com",
            password = encodedPassword,
            createdAt = LocalDateTime.now(),
            isSocialMember = false
        )
        `when`("같은 비밀번호이면") {
            val sameInputPassword = originPassword

            then("true를 반환한다") {
                user.isValidPassword(sameInputPassword, passwordEncoder) shouldBe true
            }
        }
        `when`("다른 비밀번호이면") {
            val diffrentInputPassword = originPassword + "xx"
            then("false를 반환한다") {
                user.isValidPassword(diffrentInputPassword, passwordEncoder) shouldBe false
            }
        }
    }
})

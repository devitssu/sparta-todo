package com.teamsparta.todo.domain.user.dto

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class SingUpRequestTest : BehaviorSpec({

    given("SingUpRequest를") {
        val request = SingUpRequest(
            email = "test@test.com",
            password = "test123",
            name = "testName",
        )
        `when`("User Entity로 변환하면") {
            val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
            val user = request.toEntity(passwordEncoder)

            then("암호화된 비밀번호가 들어가야 한다.") {
                user.password shouldNotBe request.password
                passwordEncoder.matches(request.password, user.password) shouldBe true
            }
            then("소셜로그인 관련 정보가 없어야 한다.") {
                user.isSocialMember shouldBe false
                user.provider shouldBe null
                user.providerId shouldBe null
            }
        }
    }
})

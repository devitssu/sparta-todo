package com.teamsparta.todo.domain.oauth

import com.teamsparta.todo.domain.user.dto.LoginResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/oauth2")
@RestController
class OAuth2LoginController(
    private val oAuth2LoginService: OAuth2LoginService,
) {

    @GetMapping("/login/kakao")
    fun redirectToLoginPage(response: HttpServletResponse) {
        oAuth2LoginService.getOAuth2LoginPage()
            .let { response.sendRedirect(it) }
    }

    @GetMapping("/callback/kakao")
    fun callback(@RequestParam code: String): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(LoginResponse(oAuth2LoginService.login(code)))
    }
}
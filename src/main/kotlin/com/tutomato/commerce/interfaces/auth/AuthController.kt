package com.tutomato.commerce.interfaces.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController : AuthApiSpec {

    @PostMapping("/token")
    override fun authorization(
        @RequestHeader("Authorization") authorization: AuthRequest.Authorization
    ): ResponseEntity<AuthResponse.Token> {
        return ResponseEntity.ok(AuthResponse.Token("this is sample token"))
    }
}
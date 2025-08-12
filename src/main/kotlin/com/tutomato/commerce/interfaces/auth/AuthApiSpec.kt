package com.tutomato.commerce.interfaces.auth

import org.springframework.http.ResponseEntity

interface AuthApiSpec {

    fun authorization(authorization: AuthRequest.Authorization): ResponseEntity<AuthResponse.Token>

}
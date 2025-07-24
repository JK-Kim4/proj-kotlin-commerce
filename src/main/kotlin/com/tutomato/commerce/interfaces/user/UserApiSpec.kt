package com.tutomato.commerce.interfaces.user

import org.springframework.http.ResponseEntity

interface UserApiSpec {

    fun user(token: UserRequest.Token): ResponseEntity<UserResponse.User>

    fun save(save: UserRequest.Save): ResponseEntity<UserResponse.User>
}
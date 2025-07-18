package com.tutomato.commerce.interfaces.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController : UserApiSpec {

    @GetMapping("/me")
    override fun user(@RequestHeader("Authorization") token: UserRequest.Token
    ): ResponseEntity<UserResponse.User> {
        return ResponseEntity.ok(UserResponse.User(1L, "test user", "test@test.com"))
    }

    @PostMapping
    override fun save(@RequestBody save: UserRequest.Save
    ): ResponseEntity<UserResponse.User> {
        return ResponseEntity.ok(UserResponse.User(1L, "saved user", "savedUser@test.com"))
    }

}
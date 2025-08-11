package com.tutomato.commerce.domain.authentication

import com.tutomato.commerce.domain.user.Authenticated
import com.tutomato.commerce.domain.user.AuthenticatedUser
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationArgumentResolverTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Authorization 헤더가 있으면 AuthenticatedUser가 주입된다`() {
        // given
        val userId = 123L
        val userName = "test user"
        val role = "USER"
        val tokenString = "$userId:$userName:$role"
        val base64Token = Base64.getEncoder().encodeToString(tokenString.toByteArray())

        // when & then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/test/me")
                .header("Authorization", base64Token)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Hello User 123"))
    }
}

@RestController
@RequestMapping("/test")
class TestAuthController {

    @GetMapping("/me")
    fun me(@Authenticated user: AuthenticatedUser): ResponseEntity<String> {
        return ResponseEntity.ok("Hello User ${user.userId}")
    }
}
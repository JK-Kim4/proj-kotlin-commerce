package com.tutomato.commerce.interfaces.user

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class UserControllerTest (
    @Autowired private val mockMvc: MockMvc) {

    @Test
    fun `인증 토큰 정보를 Header로 전달받아 사용자 정보를 조회한다`() {
        mockMvc.perform(get("/api/users/me")
                .header("Authorization", "tset-auth-token"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"userId":1,"name":"test user","email":"test@test.com"}"""))
            .andDo(document("user-me"))
    }

    @Test
    fun `사용자 정보를 전달받아 사용자를 저장한다`() {
        mockMvc.perform(post("/api/users")
                .content("""{"name": "tester", "email": "test@test.com", "password": "12345"}""")
                .contentType("application/json"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"userId":1,"name":"tester","email":"test@test.com"}"""))
            .andDo(document("user-save"))
    }
}
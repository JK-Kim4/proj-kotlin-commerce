package com.tutomato.commerce.interfaces.cart

import com.tutomato.commerce.support.domain.AuthenticationDomainSupport
import org.junit.jupiter.api.Disabled
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

@Disabled("API 구현으로 Mock API Controller TEST 수정 필요")
@WebMvcTest(CartController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class CartControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `인증 정보를 Header로 전달받아 사용자의 현재 장바구니 내용을 조회한다`() {
        mockMvc.perform(
                get("/api/carts/me")
                .header("Authorization", AuthenticationDomainSupport.사용자인증_토큰()))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """{"userId": 1,
                    "items": [
                    {"productId": 1, "quantity": 1},
                    {"productId": 2, "quantity": 4},
                    {"productId": 3, "quantity": 5}
                ]}""".trimIndent()))
            .andDo(document("cart-me"))
    }

    @Test
    fun `인증 정보를 Header로 전달받아 사용자의 장바구니에 상품을 추가한다`() {
        mockMvc.perform(
                post("/api/carts/items")
                .header("Authorization", AuthenticationDomainSupport.사용자인증_토큰())
                .content("""
                {"items": [
                    {"productId": 1, "quantity": 1},
                    {"productId": 2, "quantity": 4},
                    {"productId": 3, "quantity": 5}
                ]}""")
                .contentType("application/json"))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """{"userId": 1,
                    "items": [
                    {"productId": 1, "quantity": 1},
                    {"productId": 2, "quantity": 4},
                    {"productId": 3, "quantity": 5}
                ]}""".trimIndent()))
            .andDo(document("cart-me"))
    }

}
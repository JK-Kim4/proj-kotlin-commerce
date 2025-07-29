package com.tutomato.commerce.interfaces.order

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(OrderController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class OrderControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `인증 정보 header와 주문 상품 목록을 파라미터로 전달받아 신규 주문을 생성한다`() {
        mockMvc.perform(
            post("/api/orders")
                .header("Authorization", "test-token")
                .content("""
                {"orderLines": [
                    {"productId": 1, "quantity": 1},
                    {"productId": 2, "quantity": 4},
                    {"productId": 3, "quantity": 5}
                ]}""")
                .contentType("application/json"))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """{"orderId" : 1,
                    "status" : "PENDING",
                    "updateDateTime" : "2025-01-01T01:01"}""".trimIndent()))
            .andDo(document("order-create"))
    }

    @Test
    fun `인증 정보 header와 주문 고유번호를 전달받아 주문을 취소한다`() {
        mockMvc.perform(
            post("/api/orders/1/cancel")
                .header("Authorization", "test-token"))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """{"orderId" : 1,
                    "status" : "CANCELLED",
                    "updateDateTime" : "2025-01-01T01:01"}""".trimIndent()))
            .andDo(document("order-cancel"))
    }

}
package com.tutomato.commerce.interfaces.payment

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

@WebMvcTest(PaymentController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class PaymentControllerTest (
    @Autowired private val mockMvc: MockMvc
){

    @Test
    fun `인증 정보 header와 주문 고유 번호를 파라미터로 전달받아 결제를 진행한다`() {
        mockMvc.perform(
            post("/api/payment")
                .header("Authorization", "test-token")
                .content("""{"orderId": 1}""")
                .contentType("application/json"))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """{"paymentId" : 1,
                    "orderId" : 1,
                    "orderStatus" : "PAID",
                    "paymentDateTime" : "2025-01-01T01:01"}""".trimIndent()))
            .andDo(document("payment"))
    }

}
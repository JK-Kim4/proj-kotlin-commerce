package com.tutomato.commerce.interfaces.point

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

@WebMvcTest(PointControllerTest::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class PointControllerTest (
    @Autowired private val mockMvc: MockMvc) {

    @Test
    fun `인증 정보를 Header로 전달받아 현재 보유 포인트를 조회한다`() {
        mockMvc.perform(post("/api/points/me")
            .header("Authorization", "test-token"))
            .andExpect {
                status().isOk
                content().json(
                    """{
                            "id": 1,
                            "point": 50000
                        }""".trimIndent())
            }
            .andDo(document("points-me"))
    }




}
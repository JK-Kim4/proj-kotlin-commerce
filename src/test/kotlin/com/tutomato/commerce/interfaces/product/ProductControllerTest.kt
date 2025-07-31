package com.tutomato.commerce.interfaces.product


import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Disabled("API 구현으로 Mock API Controller TEST 수정 필요")
@WebMvcTest(ProductController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class ProductControllerTest (
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `상품 목록을 조회한다`() {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(content().json(
"""{"products": [
                    {"id": 1, "name": "테스트 상품1", "description": "테스트용 상품1입니다.", "price": 5000, "stock": 10},
                    {"id": 2, "name": "테스트 상품2", "description": "테스트용 상품2입니다.", "price": 3000, "stock": 10},
                    {"id": 3, "name": "테스트 상품3", "description": "테스트용 상품3입니다.", "price": 50000, "stock": 10}
                ]}""".trimIndent()))
            .andDo(document("products-list"))
    }

    @Test
    fun `상품 고유번호를 전달받아 상품의 상세 정보를 조회한다`() {
        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"id": 1, "name": "테스트 상품3", "description": "테스트용 상품3입니다.", "price": 50000, "stock": 10}"""))
            .andDo(document("products-detail"))
    }

    @Test
    fun `인기상품 목록을 조회한다`() {
        mockMvc.perform(get("/api/products/popular"))
            .andExpect(status().isOk)
            .andExpect(content().json(
                """{"products": [
                    {"id": 1, "name": "인기 판매 상품1", "popularityRank": 1, "salesCount": 50},
                    {"id": 2, "name": "인기 판매 상품2", "popularityRank": 2, "salesCount": 30},
                    {"id": 3, "name": "인기 판매 상품3", "popularityRank": 3, "salesCount": 20}
                ]}""".trimMargin()))
            .andDo(document("products-popular"))
    }
}
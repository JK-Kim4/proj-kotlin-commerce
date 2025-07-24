package com.tutomato.commerce.interfaces.user

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

@WebMvcTest(UserController::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class UserControllerTest (
    @Autowired private val mockMvc: MockMvc) {

    @Test
    fun `사용자 고유번호를 전달받아 상세정보를 조회한다`() {
        mockMvc.perform(get("/user/1"))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"userId":1,"userName":"hello"}"""))
            .andDo(document("user"))  // <-- 핵심: 스니펫 생성
    }
}
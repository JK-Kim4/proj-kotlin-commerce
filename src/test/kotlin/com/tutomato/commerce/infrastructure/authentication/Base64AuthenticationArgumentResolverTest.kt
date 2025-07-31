package com.tutomato.commerce.infrastructure.authentication


import com.tutomato.commerce.domain.authentication.AuthenticatedUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.core.MethodParameter
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.ServletWebRequest
import java.util.*

class Base64AuthenticationArgumentResolverTest {

    @Test
    fun `Base64 인코딩 임시 토큰을 전달받아 사용자 인증 객체 생성`() {
        // given
        val userInfo = "1:test user:USER"
        val encoded = Base64.getEncoder().encodeToString(userInfo.toByteArray())
        val resolver = Base64AuthenticationArgumentResolver()

        val request = MockHttpServletRequest()
        request.addHeader("Authorization", encoded)
        val webRequest = ServletWebRequest(request)

        // when
        val result = resolver.resolveArgument(
            parameter = DummyMethodParameter(),
            mavContainer = null,
            webRequest = webRequest,
            binderFactory = null
        ) as AuthenticatedUser

        // then
        assertEquals(1L, result.userId)
        assertEquals("test user", result.userName)
        assertTrue(result.roles.any { it.name == "USER" })
    }


}

class DummyMethodParameter : MethodParameter(Any::class.java.getDeclaredMethods()[0], -1)
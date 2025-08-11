package com.tutomato.commerce.infrastructure.user.authentication

import com.tutomato.commerce.domain.user.Authenticated
import com.tutomato.commerce.domain.user.AuthenticatedUser
import com.tutomato.commerce.domain.user.AuthenticationArgumentResolver
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.*

@Component
class Base64AuthenticationArgumentResolver : AuthenticationArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(Authenticated::class.java) != null &&
                parameter.parameterType == AuthenticatedUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): Any {
        val token = webRequest.getHeader("Authorization") ?: throw IllegalArgumentException("No Authorization header found")
        val decoded = decodeBase64Token(token)
        val (userId, userName, roles) = parseToken(decoded)
        return AuthenticatedUser(userId, userName, roles)
    }

    private fun decodeBase64Token(token: String): String {
        return try {
            String(Base64.getDecoder().decode(token))
        } catch (e: IllegalArgumentException) {
            //TODO e stack trace
            throw IllegalArgumentException("Invalid base64 token")
        }
    }

    private fun parseToken(decoded: String): Triple<Long, String, String> {
        val parts = decoded.split(":")
        if (parts.size != 3) throw IllegalArgumentException("Invalid token format")
        val userId = parts[0].toLongOrNull() ?: throw IllegalArgumentException("Invalid user ID")
        val userName = parts[1]
        val roles = parts[2]
        return Triple(userId, userName, roles)
    }
}
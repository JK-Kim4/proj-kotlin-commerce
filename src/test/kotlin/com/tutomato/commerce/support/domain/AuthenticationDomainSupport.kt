package com.tutomato.commerce.support.domain

import java.util.*

object AuthenticationDomainSupport {

    fun 사용자인증_토큰(): String{
        val userInfo = "1:test user:USER"
        return Base64.getEncoder().encodeToString(userInfo.toByteArray())
    }
}
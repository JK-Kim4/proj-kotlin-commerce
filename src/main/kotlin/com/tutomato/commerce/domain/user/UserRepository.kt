package com.tutomato.commerce.domain.user

import java.math.BigDecimal

interface UserRepository {

    fun findById(userId: Long): User?

    fun chargeUserPoint(userId: Long, amount: BigDecimal): User?

    fun deductedUserPoint(userId: Long, amount: BigDecimal): User?

}
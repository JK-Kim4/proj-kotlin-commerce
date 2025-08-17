package com.tutomato.commerce.infrastructure.payment

import com.tutomato.commerce.domain.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PaymentJpaRepository: JpaRepository<Payment, Long> {

    fun findByOrderId(orderId: Long): Optional<Payment>
}
package com.tutomato.commerce.infrastructure.payment

import com.tutomato.commerce.domain.payment.Payment
import com.tutomato.commerce.domain.payment.PaymentRepository
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository,
) : PaymentRepository {

    override fun save(payment: Payment): Payment {
        return paymentJpaRepository.save(payment)
    }

    override fun findById(id: Long): Payment? {
        return paymentJpaRepository.findById(id).orElse(null)
    }

    override fun findByOrderId(id: Long): Payment? {
        return paymentJpaRepository.findByOrderId(id).orElse(null)
    }
}
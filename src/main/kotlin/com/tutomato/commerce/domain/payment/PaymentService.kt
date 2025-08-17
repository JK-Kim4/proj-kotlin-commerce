package com.tutomato.commerce.domain.payment

import jakarta.persistence.NoResultException
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {

    fun save(command: PaymentCommand.Pay): PaymentResult.Payment {
        val payment = paymentRepository.save(command.toEntity())

        return PaymentResult.Payment.from(payment)
    }

    fun findById(id: Long): PaymentResult.Payment {
        val payment = paymentRepository.findById(id)
            ?: throw NoResultException("Payment with id $id not found")

        return PaymentResult.Payment.from(payment)
    }

    fun pay(command: PaymentCommand.Pay): PaymentResult.Payment {
        val payment = paymentRepository.findByOrderId(command.orderId)
            ?: throw NoResultException("Payment with id $command.orderId not found")

        payment.pay(command.method!!, command.type!!)

        return PaymentResult.Payment.from(payment)
    }

    fun findByOrderId(orderId: Long): PaymentResult.Payment {
        val payment = paymentRepository.findByOrderId(orderId)
        ?: throw NoResultException("Payment with id $orderId not found")

        return PaymentResult.Payment.from(payment)
    }

}
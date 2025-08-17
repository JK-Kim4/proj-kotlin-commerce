package com.tutomato.commerce.domain.payment


interface PaymentRepository {

    fun save(payment: Payment): Payment
    fun findById(id: Long): Payment?
    fun findByOrderId(id: Long): Payment?

}
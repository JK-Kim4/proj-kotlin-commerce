package com.tutomato.commerce.application.payment

import com.tutomato.commerce.domain.payment.PaymentCommand
import com.tutomato.commerce.domain.payment.PaymentPaidEvent
import com.tutomato.commerce.domain.payment.PaymentService
import com.tutomato.commerce.domain.user.UserCommand
import com.tutomato.commerce.domain.user.UserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PaymentUserFacade(
    private val userService: UserService,
    private val paymentService: PaymentService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun payment(command: PaymentCommand.Pay) {
        // 1.사용자 포인트 차감
        userService.deductPoint(UserCommand.DeductPoint(command.userId, command.amount.value))

        // 2. 결제
        val paymentResult = paymentService.pay(command)

        // 3. 결제 이벤트 발행
        applicationEventPublisher.publishEvent(PaymentPaidEvent(paymentResult.paymentId, paymentResult.orderId))
    }
}
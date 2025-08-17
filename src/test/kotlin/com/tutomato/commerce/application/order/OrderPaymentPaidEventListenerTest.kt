package com.tutomato.commerce.application.order

import com.tutomato.commerce.application.payment.PaymentUserFacade
import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.order.Order
import com.tutomato.commerce.domain.order.OrderCommand
import com.tutomato.commerce.domain.order.OrderRepository
import com.tutomato.commerce.domain.order.OrderStatus
import com.tutomato.commerce.domain.payment.Payment
import com.tutomato.commerce.domain.payment.PaymentCommand
import com.tutomato.commerce.domain.payment.PaymentMethod
import com.tutomato.commerce.domain.payment.PaymentRepository
import com.tutomato.commerce.domain.payment.PaymentType
import com.tutomato.commerce.domain.user.UserRepository
import com.tutomato.commerce.support.async.AsyncSupport.Companion.waitUntilNotNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@SpringBootTest
@Testcontainers
@Sql(
    scripts = [
        "classpath:data/product.sql",
        "classpath:data/user.sql",
        "classpath:data/order.sql",
        "classpath:data/payment.sql", ],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class OrderPaymentPaidEventListenerTest(
    @Autowired private val orderProductFacade: OrderProductFacade,
    @Autowired private val paymentUserFacade: PaymentUserFacade,
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val paymentRepository: PaymentRepository,
    @Autowired private val userRepository: UserRepository,
) {

    private val USERID = 1L
    private val PENDINGORDERID = 1L

    @Test
    fun `결제 완료 처리 이벤트 발행 테스트`() {
        //given
        val command = PaymentCommand.Pay(
            orderId = PENDINGORDERID,
            userId = USERID,
            amount = Money(BigDecimal.valueOf(100000)),
            method = PaymentMethod.POINT,
            type = PaymentType.PAY
        )

        //when
        paymentUserFacade.payment(command)
        val order: Order? = waitUntilNotNull(timeoutSeconds = 3) {
            orderRepository.findById(PENDINGORDERID)
        }
        val user = userRepository.findById(USERID)

        //then
        assertThat(user!!.balance.balance.value.toLong()).isEqualTo(BigDecimal.ZERO.toLong()) //포인트 차감 확인
        assertThat(order!!.orderStatus).isEqualTo(OrderStatus.PAID) //주문 상태가 결제 완료로 변경되었는지 확인
    }

}
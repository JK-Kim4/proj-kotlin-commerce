package com.tutomato.commerce.application.order

import com.tutomato.commerce.domain.order.OrderRepository
import com.tutomato.commerce.domain.payment.PaymentRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@Sql(
    scripts = [
        "classpath:data/product.sql",
        "classpath:data/user.sql",
        "classpath:data/order.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class OrderPaymentPaidEventListenerTest(
    @Autowired private val orderProductFacade: OrderProductFacade,
    @Autowired private val orderRepository: OrderRepository,
    @Autowired private val paymentRepository: PaymentRepository,
) {

    @Test
    fun `테스트 데이터 등록 확인`() {
        print(orderRepository.findAll().size)
        print(orderRepository.findOrderLinesAll().size)
    }

}
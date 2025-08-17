package com.tutomato.commerce.application.payment

import com.tutomato.commerce.application.order.OrderProductFacade
import com.tutomato.commerce.domain.order.OrderCommand
import com.tutomato.commerce.domain.payment.Payment
import com.tutomato.commerce.domain.payment.PaymentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.time.Duration

@SpringBootTest
@Testcontainers
@Sql(
    scripts = ["classpath:data/product.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class PaymentOrderCreatedEventListenerTest(
    @Autowired private val orderProductFacade: OrderProductFacade,
    @Autowired private val paymentRepository: PaymentRepository,
) {

    @Test
    fun `주문 생성 이벤트를 수신하여 Payment 객체를 생성한다`() {
        //given
        val command = OrderCommand.OrderSaveCommand(
            userId = 1L,
            snapshots = snapshots
        )

        //when
        val orderResult = orderProductFacade.order(command)
        val payment: Payment? = waitUntilNotNull(timeoutSeconds = 3) {
            paymentRepository.findByOrderId(orderResult.id)
        }

        //then
        assertThat(payment).isNotNull
        assertThat(payment!!.amount.value.toLong()).isEqualTo(orderResult.subTotal.toLong())
    }

    private fun <T> waitUntilNotNull(timeoutSeconds: Long, check: () -> T?): T? {
        val start = System.currentTimeMillis()
        val timeout = Duration.ofSeconds(timeoutSeconds).toMillis()
        while (System.currentTimeMillis() - start < timeout) {
            val result = check()
            if (result != null) return result
            Thread.sleep(100)
        }
        return null
    }

    val snapshots = listOf(
        OrderCommand.OrderProductSnapshot(
            productId = 1,
            optionId = 1,
            price = BigDecimal.valueOf(50000),
            quantity = 1,
        )
    )

}
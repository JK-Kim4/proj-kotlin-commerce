package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderTest {

    val 상품가격 = Money(BigDecimal.valueOf(5000))
    val orderLineList = listOf(
        OrderLine(
            ProductSnapshot(
                productId = 1L,
                optionId = 2L,
                price = 상품가격,
                quantity = 1
            )
        ),
        OrderLine(
            ProductSnapshot(
                productId = 1L,
                optionId = 3L,
                price = 상품가격,
                quantity = 2
            )
        ),
        OrderLine(
            ProductSnapshot(
                productId = 1L,
                optionId = 4L,
                price = 상품가격,
                quantity = 3
            )
        ),
    )

    @Test
    fun `주문 객체 생성 시 주문 주문 금액을 초기화한다`() {
        //when
        val order = Order.create(userId = 1L, lines = orderLineList,)

        //then
        assertThat(order.orderAmounts).isNotNull
        assertThat(order.orderAmounts.subTotal).isEqualTo(상품가격 * 6)
    }
}
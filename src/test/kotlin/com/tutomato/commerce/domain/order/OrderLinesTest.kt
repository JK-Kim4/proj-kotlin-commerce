package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderLinesTest {

    val 상품가격 = Money(BigDecimal.valueOf(50000))
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
    fun `주문 상품의 목록을 전달받아 OrderLiens 객체 생성`(){
        //when
        val orderLines = OrderLines(orderLineList)

        //then
        assertThat(orderLines).isNotNull
        assertThat(orderLines.size()).isEqualTo(orderLineList.size)
    }

    @Test
    fun `주문 상품의 주문 금액의 합을 계산`() {
        //when
        val orderLines = OrderLines(orderLineList)
        val subTotalPrice = orderLines.calculateSubTotal()

        //then
        assertThat(subTotalPrice).isNotNull
        assertThat(subTotalPrice).isEqualTo(상품가격 * 6)
    }
}
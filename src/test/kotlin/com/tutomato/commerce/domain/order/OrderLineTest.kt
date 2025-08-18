package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderLineTest {

    val 상품가격 = Money(BigDecimal.valueOf(50000))
    val 주문수량 = 10
    val snapshot = ProductSnapshot(
        productId = 1L,
        optionId = 1L,
        price = 상품가격,
        quantity = 주문수량
    )

    @Nested
    @DisplayName("주문 상품 생성")
    inner class OrderLineCreate {

        @Test
        fun `주문 상품 객체 생성`() {
            //when
            val orderLine = OrderLine(snapshot)

            //then
            assertThat(orderLine).isNotNull
        }

        @Test
        fun `주문 상품의 ProductSnapshot의 가격과 수량 정보를 계산하여 상품 가격 초기화`() {
            //when
            val orderLine = OrderLine(snapshot)

            //then
            assertThat(orderLine.price).isEqualTo(상품가격 * 주문수량)

        }

    }
}
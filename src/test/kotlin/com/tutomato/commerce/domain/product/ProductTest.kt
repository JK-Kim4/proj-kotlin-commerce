package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.time.LocalDateTime
import kotlin.test.Test

class ProductTest {

    @Nested
    @DisplayName("상품 판매 가능 여부 확인 시")
    inner class 판매가능여부 {

        var product: Product? = null
        val option1 = OptionDomainSupport.fixture(id = 1L, stock = 10)
        val option2 = OptionDomainSupport.fixture(id = 2L, color = "f00000", stock = 1)

        @BeforeEach
        fun init() {
            product = ProductDomainSupport.fixture(
                saleStatus = SaleStatus.ON_SALE,
                options = Options(listOf(option1, option2))
            )
        }

        @Test
        @DisplayName("상품의 판매상태가 ON_SALE이고 옵션에 재고가 충분할 경우 true")
        fun return_true() {
            assertThat(product!!.canOrder(option1, 10)).isTrue()
        }

        @Test
        @DisplayName("상품의 판매상태가 ON_SALE이 아닐 경우 false")
        fun return_false1() {
            product!!.stopSales(LocalDateTime.now())
            assertThat(product!!.canOrder(option1, 10)).isFalse()

            product!!.soldOut(LocalDateTime.now())
            assertThat(product!!.canOrder(option1, 10)).isFalse()
        }

        @Test
        @DisplayName("주문 옵션의 재고수가 부족한 경우 false")
        fun return_false2() {
            val 보유재고초과수량 = option1.stock.stock + 1;
            assertThat(product!!.canOrder(option1, 보유재고초과수량)).isFalse()
        }
    }


}
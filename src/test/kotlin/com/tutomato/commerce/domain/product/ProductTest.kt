package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime


class ProductTest {


    private val 상품옵션1_재고10개 = OptionDomainSupport.fixture(id = 1L, stock = 10)
    private val 상품옵션2_재고1개 = OptionDomainSupport.fixture(id = 2L, color = "f00000", stock = 1)
    private lateinit var product: Product


    @Nested
    @DisplayName("상품 판매 가능 여부 확인")
    inner class ProductCanOrderTest {

        @Test
        fun `상품의 판매상태가 ON_SALE이고 재고가 충분할 경우 true`() {
            assertThat(product.canOrder(상품옵션1_재고10개, 10)).isTrue()
        }

        @Test
        fun `상품의 판매상태가 ON_SALE이 아닐 경우 false`() {
            product.stopSales(LocalDateTime.now())
            assertThat(product.canOrder(상품옵션1_재고10개, 10)).isFalse()

            product.soldOut(LocalDateTime.now())
            assertThat(product.canOrder(상품옵션1_재고10개, 10)).isFalse()
        }

        @Test
        fun `주문 옵션의 재고가 부족한 경우 false`() {
            assertThat(product.canOrder(상품옵션2_재고1개, 2)).isFalse()
        }
    }

    @Test
    fun `상품 옵션 목록을 초기화한다`() {
        //given
        val product = ProductDomainSupport.fixture()
        val optionList = listOf(
            OptionDomainSupport.fixture(1L, color = "testcolor1", stock = 1),
            OptionDomainSupport.fixture(2L, color = "testcolor2", stock = 2),
            OptionDomainSupport.fixture(3L, color = "testcolor3", stock = 3),
        )

        //when
        product.initializeOptions(optionList)

        //then
        assertThat(product.availableOptions.options).isEqualTo(optionList)
    }

    @Nested
    @DisplayName("상품 옵션 등록 여부")
    inner class ExistOptions {

        @Test
        fun `동일한 Color와 Size의 옵션이 존재할 경우 true`() {
            assertThat(product.alreadyExistOption(상품옵션1_재고10개.color.code, 상품옵션1_재고10개.size.name)).isTrue()
        }

        @Test
        fun `동일한 Color와 Size의 옵션이 존재하지 않을 경우 false`() {
            //given
            val 미등록상품옵션 = OptionDomainSupport.fixture(color = "미등록상품Color", size = Size.XXL.name)

            //when then
            assertThat(product.alreadyExistOption(미등록상품옵션.color.code, 미등록상품옵션.size.name)).isFalse()
        }
    }


    @BeforeEach
    fun init() {
        product = ProductDomainSupport.fixture(
            saleStatus = SaleStatus.ON_SALE,
            options = Options(listOf(상품옵션1_재고10개, 상품옵션2_재고1개))
        )
    }
}
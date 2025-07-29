package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProductTest {


    @Test
    fun `현재 상품의 주문 가능 여부를 검증한다`() {
        val product = ProductDomainSupport.기본_상품_생성()

        assertThat(product.canOrder()).isTrue()
    }

    @Test
    fun `상품 옵션 고유번호에 해당하는 옵션을 상품이 보유하고 있을 경우 옵션 정보를 반환한다`() {
        val optionId = 90L
        val option = OptionDomainSupport.옵션_생성_고유번호(optionId)
        val product = ProductDomainSupport.상품_옵션_전달받아_상품_생성(option)

        //when
        val findOption = product.optionById(optionId)

        //then
        assertThat(findOption).isNotNull()
        assertThat(findOption).isEqualTo(option)
    }

    @Test
    fun `상품 옵션 고유번호에 해당하는 옵션을 상품이 보유하고있지 않을 경우 NoSuchElement 오류를 반환한다`() {
        val product = ProductDomainSupport.기본_상품_생성()
        val nonExistOptionId = 99L

        assertThatThrownBy { product.optionById(nonExistOptionId) }
            .isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    fun `상품 옵션 객체를 파라미터로 전달하여 옵션을 제거할 수 있다`() {
        val option = OptionDomainSupport.기본_옵션_생성()
        val product = ProductDomainSupport.상품_옵션_전달받아_상품_생성(option)
        assertThat(product.getOptions().options).contains(option)

        //when
        product.removeOption(option)
        val options = product.getOptions().options

        //then
        assertThat(options).doesNotContain(option)
    }

    @Test
    fun `상품 옵션 고유번호룰 파라미터로 전달하여 옵션을 제거할 수 있다`() {
        val optionId = 99L
        val option = OptionDomainSupport.옵션_생성_고유번호(optionId)
        val product = ProductDomainSupport.상품_옵션_전달받아_상품_생성(option)
        assertThat(product.getOptions().options).contains(option)

        //when
        product.removeOption(optionId)
        val options = product.getOptions().options

        //then
        assertThat(options).doesNotContain(option)
    }

    @Test
    fun `상품의 판매 상태를 SALE_STOPPED로 변경한다 `() {
        val product = ProductDomainSupport.기본_상품_생성()
        assertThat(product.saleStatus).isEqualTo(SaleStatus.ON_SALE)

        product.stopSales(LocalDateTime.now())

        assertThat(product.saleStatus).isEqualTo(SaleStatus.SALE_STOPPED)
    }

    @Test
    fun `상품의 판매 상태를 SOLD_OUT로 변경한다 `() {
        val product = ProductDomainSupport.기본_상품_생성()
        assertThat(product.saleStatus).isEqualTo(SaleStatus.ON_SALE)

        product.soldOut(LocalDateTime.now())

        assertThat(product.saleStatus).isEqualTo(SaleStatus.SOLD_OUT)
    }

    @Test
    fun `상품의 판매 상태를 ON_SALE로 변경한다 `() {
        val product = ProductDomainSupport.기본_상품_생성()
        product.soldOut(LocalDateTime.now())
        assertThat(product.saleStatus).isEqualTo(SaleStatus.SOLD_OUT)

        product.resumeSales(LocalDateTime.now())

        assertThat(product.saleStatus).isEqualTo(SaleStatus.ON_SALE)
    }
}
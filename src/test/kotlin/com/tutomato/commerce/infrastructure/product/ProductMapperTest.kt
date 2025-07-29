package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Color
import com.tutomato.commerce.domain.product.Size
import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.junit.jupiter.api.Test

class ProductMapperTest {

    @Test
    fun `상품 고유번호를 지표로 옵션객체 맵핑을 수행한다`() {
        var product1 = ProductDomainSupport.고유번호를_보유한_상품객체_생성(1L)
        var product2 = ProductDomainSupport.고유번호를_보유한_상품객체_생성(2L)

        var option1 = OptionDomainSupport.옵션_생성_COLOR_SIZE_PRODUCTID(Color("f111111"), Size.XS, product1)
        var option2 = OptionDomainSupport.옵션_생성_COLOR_SIZE_PRODUCTID(Color("f111112"), Size.XS, product1)
        var option3 = OptionDomainSupport.옵션_생성_COLOR_SIZE_PRODUCTID(Color("f111111"), Size.XS, product2)
        var option4 = OptionDomainSupport.옵션_생성_COLOR_SIZE_PRODUCTID(Color("f111112"), Size.XS, product2)


        val products = ProductMapper.optionsMappingToProduct(
            listOf(option1, option2, option3, option4),
            listOf(product1, product2),
        )

        products.forEach { product ->
            val allMatch = product.availableOptions.options.all { it.product?.equals(product) ?: false }
            assert(allMatch) { "옵션의 productId가 상품의 id와 일치하지 않습니다: ${product.id}" }
        }
    }

}
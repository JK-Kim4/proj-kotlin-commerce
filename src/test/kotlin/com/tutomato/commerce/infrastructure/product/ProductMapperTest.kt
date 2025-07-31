package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.junit.jupiter.api.Test

class ProductMapperTest {

    @Test
    fun `동일한 상품에 대한 옵션 정보를 맵핑한다`() {
        //given
        val 상품1 = ProductDomainSupport.fixture(id = 1L, name = "상품 1")
        val 상품2 = ProductDomainSupport.fixture(id = 2L, name = "상품 2")
        val 옵션1_1 = OptionDomainSupport.fixture(1L, 상품1)
        val 옵션1_2 = OptionDomainSupport.fixture(1L, 상품1)
        val 옵션1_3 = OptionDomainSupport.fixture(1L, 상품1)
        val 옵션2_1 = OptionDomainSupport.fixture(2L, 상품2)
        val 옵션2_2 = OptionDomainSupport.fixture(2L, 상품2)

        //when
        val mappingResult = ProductMapper.optionsMappingToProduct(
            listOf(상품1, 상품2),
            listOf(옵션1_1, 옵션1_2, 옵션1_3, 옵션2_1, 옵션2_2)
        )

        assertThat(mappingResult.find { it.id == 1L }!!.availableOptions.options)
            .containsExactly(옵션1_1, 옵션1_2, 옵션1_3)
        assertThat(mappingResult.find { it.id == 2L }!!.availableOptions.options)
            .containsExactly(옵션2_1, 옵션2_2)
    }
}
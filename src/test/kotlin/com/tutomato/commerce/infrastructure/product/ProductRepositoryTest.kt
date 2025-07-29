package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Color
import com.tutomato.commerce.domain.product.ProductRepository
import com.tutomato.commerce.domain.product.Size
import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class ProductRepositoryTest(
    @Autowired var productRepository: ProductRepository,
    @Autowired var optionJpaRepository: OptionJpaRepository
) {

    @Test
    fun `상품 저장 시 옵션 정보가 함께 영속화된다`() {
        //given
        var options = listOf(
            OptionDomainSupport.옵션_생성_COLOR_SIZE(Color("f000000"), Size.M),
            OptionDomainSupport.옵션_생성_COLOR_SIZE(Color("f000001"), Size.S),
            OptionDomainSupport.옵션_생성_COLOR_SIZE(Color("f000002"), Size.L)
        )
        var product = ProductDomainSupport.상품_옵션_목록을_전달받아_상품_생성(options)

        //when
        productRepository.save(product)
        val savedOption  = optionJpaRepository.findByProductId(product.id!!)

        //then
        assertThat(savedOption).isEqualTo(options)
    }


}
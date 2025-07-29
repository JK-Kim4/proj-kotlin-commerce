package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class ProductServiceTest(
    @Autowired val productService: ProductService,
    @Autowired val productRepository: ProductRepository,
) {

    @Test
    fun `상품 재고 차감 요청 시 재고가 충분할 경우 차감이 진행되고 결과를 영속화한다`() {
        //given
        var product = ProductDomainSupport.기본_상품_생성()
        val option = OptionDomainSupport.옵션_생성_STOCK(10)
        product.addOption(option)

        productRepository.save(product)
        val command = DecreaseStock(option.productId, option.id, 5);

        //when
        productService.decreaseStock(command)
        val updatedOption = productRepository.findOptionById(option.id)

        //then
        assertThat(updatedOption.stock).isEqualTo(Stock(5))

    }


}
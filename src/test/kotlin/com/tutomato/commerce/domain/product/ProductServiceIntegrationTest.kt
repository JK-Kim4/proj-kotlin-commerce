package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class ProductServiceIntegrationTest(
    @Autowired val productService: ProductService,
    @Autowired val productRepository: ProductRepository,
) {

    lateinit var product: Product
    lateinit var option: Option

    @BeforeEach
    fun init() {
        product = ProductDomainSupport.fixture(
            saleStatus = SaleStatus.ON_SALE,
            options = Options(
                listOf(OptionDomainSupport.fixture(stock = 10))
            ))
        productRepository.save(product)

        option = product.availableOptions.options.first()
    }


    @Test
    fun `상품 재고를 차감한다`(){
        //given
        val command = DecreaseStock(productId = product.id, optionId = option.id, 9)

        //when
        productService.decreaseStock(command)
        val decreaseResult = productRepository.findOptionById(option.id)!!

        //then
        assertThat(decreaseResult.stock).isEqualTo(Stock(1))
    }

    @Test
    fun `재고가 부족한 경우 IllegalArgumentException`(){
        val product = ProductDomainSupport.fixture()
        productRepository.save(product)
        val option = OptionDomainSupport.fixture(product = product, stock = 10)
        productRepository.save(option)
        val command = DecreaseStock(productId = product.id, optionId = option.id, 11)

        assertThrows<IllegalArgumentException> { productService.decreaseStock(command) }
    }

    @Test
    fun `상품의 판매 상태를 변경한다`() {
        //given
        val command = UpdateStatus(productId = product.id, updateStatus = SaleStatus.SALE_STOPPED.name)

        //when
        productService.updateStatus(command)
        val product = productRepository.findById(command.productId)!!

        //then
        assertThat(product.saleStatus).isEqualTo(SaleStatus.SALE_STOPPED)
    }
}
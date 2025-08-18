package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class ProductServiceIntegrationTest(
    @Autowired private val productService: ProductService,
    @Autowired private val productRepository: ProductRepository,
) {


    private val 옵션초기수량 = 10
    private lateinit var 등록상품: Product
    private lateinit var 상품판매옵션: Option


    @Test
    fun `재고를 차감한다`(){
        //given
        val command = ProductCommand.DecreaseStock(productId = 등록상품.id, optionId = 상품판매옵션.id, 9)

        //when
        productService.decreaseStock(command)
        val decreaseResult = productRepository.findOptionById(상품판매옵션.id)!!

        //then
        assertThat(decreaseResult.stock).isEqualTo(Stock(1))
    }

    @Test
    fun `상품의 판매 상태를 변경한다`() {
        //given
        val command = ProductCommand.UpdateStatus(productId = 등록상품.id, updateStatus = SaleStatus.SALE_STOPPED.name)

        //when
        productService.updateStatus(command)
        val product = productRepository.findById(command.productId)!!

        //then
        assertThat(product.saleStatus).isEqualTo(SaleStatus.SALE_STOPPED)
    }


    @BeforeEach
    fun init() {
        등록상품 = ProductDomainSupport.fixture(
            saleStatus = SaleStatus.ON_SALE,
            options = Options(
                listOf(OptionDomainSupport.fixture(stock = 옵션초기수량))
            ))
        productRepository.save(등록상품)
        상품판매옵션 = 등록상품.availableOptions.options.first()
    }
}
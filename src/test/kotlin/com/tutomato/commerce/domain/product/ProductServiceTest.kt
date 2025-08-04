package com.tutomato.commerce.domain.product

import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.NoResultException
import org.junit.jupiter.api.*
import javax.management.InstanceAlreadyExistsException

class ProductServiceTest {

    private val 상품고유번호 = 1L
    private val 상품옵션고유번호01 = 10L
    private val 미등록상품고유번호 = 999L
    private val 미등록옵션고유번호 = 9999L
    private val 상품옵션초기재고 = 10
    private val 등록상품 = ProductDomainSupport.fixture(id = 상품고유번호, )
    private val 상품옵션 = OptionDomainSupport.fixture(id = 상품옵션고유번호01, product = 등록상품, stock = 상품옵션초기재고)
    private lateinit var productService: ProductService
    private lateinit var productRepository: ProductRepository


    @Nested
    @DisplayName("상품 옵션 등록")
    inner class OptionSave {

        @Test
        fun `상품이 존재하지 않을 경우 NoResultException 오류`() {
            //given
            val command = ProductOptionSave(
                productId = 미등록상품고유번호,
                colorCode = "testcode",
                size = Size.XXL.name,
                stock = 10
            )

            //when then
            assertThrows<NoResultException> { productService.save(command) }
        }

        @Test
        fun `동일한 옵션이 이미 등록되어있을 경우 InstanceAlreadyExistsException 오류`() {
            // given
            val command = ProductOptionSave(
                productId = 상품고유번호,
                colorCode = 상품옵션.color.code,
                size = 상품옵션.size.name,
                stock = 10
            )

            //when then
            assertThrows<InstanceAlreadyExistsException> { productService.save(command) }
        }
    }


    @Nested
    @DisplayName("상품 옵션 재고 차감")
    inner class DecreaseStock {

        @Test
        fun `상품 옵션이 존재하지 않을 경우 NoResultException 오류`() {
            //given
            val command = DecreaseStock(
                productId = 상품고유번호,
                optionId = 미등록옵션고유번호,
                decreaseAmount = 10
            )

            //when then
            assertThrows<NoResultException> { productService.decreaseStock(command) }
        }

        @Test
        fun `상품 옵션의 재고가 부족할 경우 IllegalArgumentException 오류`() {
            //given
            val command = DecreaseStock(
                productId = 상품고유번호,
                optionId = 상품옵션고유번호01,
                decreaseAmount = 상품옵션초기재고 + 1
            )

            //when then
            assertThrows<IllegalArgumentException> { productService.decreaseStock(command) }
        }
    }


    @Test
    fun `상품 판매 상태 수정 요청 시 상품이 존재하지 않을 경우 NoResultException 오류`() {
        //given
        val command = UpdateStatus(
            productId = 미등록상품고유번호,
            updateStatus = SaleStatus.SOLD_OUT.name
        )

        //when then
        assertThrows<NoResultException> { productService.updateStatus(command) }
    }


    @BeforeEach
    fun init() {
        등록상품.availableOptions = Options(listOf(상품옵션))

        productRepository = mockk()
        productService = ProductService(productRepository)

        every { productRepository.findById(상품고유번호) } returns 등록상품
        every { productRepository.findOptionById(상품옵션고유번호01) } returns 상품옵션
        every { productRepository.findById(미등록상품고유번호) } returns null
        every { productRepository.findOptionById(미등록옵션고유번호) } returns null
    }
}
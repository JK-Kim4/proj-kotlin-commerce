package com.tutomato.commerce.domain.cart

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.mockito.ArgumentMatchers.any
import javax.management.InstanceAlreadyExistsException

class CartServiceTest {


    private val 테스트상품고유번호: Long = 1L
    private val 테스트상품옵션고유번호: Long = 1L
    private var 테스트장바구니: Cart = generateCart()
    private lateinit var cartService: CartService
    private lateinit var cartRepository: CartRepository


    @Nested
    @DisplayName("장바구니 상품 등록")
    inner class addCartItemTest {

        @Test
        fun `동일 상품 옵션이 이미 존재할 경우 InstanceAlreadyExistsException 오류 발생`() {
            //given
            val command = CartCommand.SaveCartItem(10L, 테스트상품고유번호, 테스트상품옵션고유번호, 10)
            every { cartRepository.findByUserId(10) } returns 테스트장바구니

            //when
            assertThrows<InstanceAlreadyExistsException> { cartService.save(command) }
        }

        @Test
        fun `동일 상품 옵션이 존재하지 않을 경우 경우 신규 등록 실행`() {
            //given
            val 신규추가상품고유번호 = 99L
            val 신규추가상품옵션고유번호 = 999L
            val command = CartCommand.SaveCartItem(10L, 신규추가상품고유번호, 신규추가상품옵션고유번호, 10)
            every { cartRepository.findByUserId(10) } returns 테스트장바구니
            every { cartRepository.save(any<CartItem>()) } returns CartItem(
                id = 1L,
                productId = 신규추가상품고유번호,
                optionId = 신규추가상품옵션고유번호,
                quantity = 10,
                cart = 테스트장바구니)

            //when
            cartService.save(command)

            //then
            verify { cartRepository.save(any<CartItem>()) }
        }
    }


    @Test
    fun `이미 등록된 상품 옵션의 수량을 변경`() {
        //given
        val 장바구니상품 = CartItem(
            id = 100,
            productId = 테스트상품고유번호,
            optionId = 테스트상품옵션고유번호,
            quantity = 10,
            cart = 테스트장바구니
        )
        val command = CartCommand.UpdateItemAmount(cartItemId = 100, updateAmount = 3)
        every { cartRepository.findItemById(100) } returns 장바구니상품
        every { cartRepository.save(any<CartItem>()) } returns any<CartItem>()


        //when
        cartService.updateItemAmount(command)

        //then
        assertThat(장바구니상품.quantity).isEqualTo(3)
    }


    @BeforeEach
    fun init() {
        cartRepository = mockk()
        cartService = CartService(cartRepository)
    }

    private fun generateCart() : Cart {
        return Cart(id = 5,userId = 10)
            .also {
                it.cartItems = CartItems(listOf(
                    CartItem(
                        productId = 테스트상품고유번호,
                        optionId = 테스트상품옵션고유번호,
                        quantity = 100,
                        cart = it),
                    )
                )
            }
    }
}
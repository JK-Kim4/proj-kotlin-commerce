package com.tutomato.commerce.domain.cart

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import javax.management.InstanceAlreadyExistsException

class CartServiceTest {

    private lateinit var cartService: CartService
    private lateinit var cartRepository: CartRepository

    @BeforeEach
    fun init() {
        cartRepository = mockk()
        cartService = CartService(cartRepository)
    }

    @Test
    fun `장바구니에 동일한 상품과 옵션이 등록되어있을 경우 신규 추가 시 오류 발생`() {
        //given
        val 테스트상품고유번호 = 1L
        val 테스트상품옵션고유번호 = 1L
        val 테스트장바구니 = Cart(id = 5,userId = 10)
            .also {
                it.cartItems = CartItems(listOf(CartItem(
                    productId = 테스트상품고유번호,
                    optionId = 테스트상품옵션고유번호,
                    quantity = 100,
                    cart = it)))
            }
        val command = CartCommand.SaveCartItem(10L, 테스트상품고유번호, 테스트상품옵션고유번호, 10)
        every { cartRepository.findByUserId(10) } returns 테스트장바구니

        //when
        assertThrows<InstanceAlreadyExistsException> { cartService.save(command) }
    }

    @Test
    fun `장바구니에 동일한 상품과 옵션이 존재하지 않을 경우 경우 신규 등록 실행`() {
        //given
        val 테스트상품고유번호 = 1L
        val 테스트상품옵션고유번호 = 1L
        val 테스트장바구니 = Cart(id = 5,userId = 10, cartItems = CartItems())
        val 테스트장바구니상품 = CartItem(
            productId = 테스트상품고유번호,
            optionId = 테스트상품옵션고유번호,
            quantity = 10,
            cart = 테스트장바구니)
        val command = CartCommand.SaveCartItem(10L, 테스트상품고유번호, 테스트상품옵션고유번호, 10)
        every { cartRepository.findByUserId(10) } returns 테스트장바구니
        every { cartRepository.save(any<CartItem>()) } returns CartItem(
            id = 1L,
            productId = 테스트상품고유번호,
            optionId = 테스트상품옵션고유번호,
            quantity = 10,
            cart = 테스트장바구니)

        //when
        cartService.save(command)

        //then
        verify { cartRepository.save(any<CartItem>()) }
    }

    @Test
    fun `장바구니에 상품이 등록되어있을 경우 수량을 변경`() {
        //given
        val 테스트상품고유번호 = 1L
        val 테스트상품옵션고유번호 = 1L
        val 테스트장바구니 = Cart(id = 5,userId = 10)
        val 장바구니상품 = CartItem(id = 100, productId = 테스트상품고유번호, optionId = 테스트상품옵션고유번호, quantity = 10, cart = 테스트장바구니)
        val command = CartCommand.UpdateItemAmount(cartItemId = 100, updateAmount = 3)
        every { cartRepository.findItemById(100) } returns 장바구니상품
        every { cartRepository.save(any<CartItem>()) } returns any<CartItem>()


        //when
        cartService.updateItemAmount(command)

        //then
        assertThat(장바구니상품.quantity).isEqualTo(3)
    }
}
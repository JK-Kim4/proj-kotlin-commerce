package com.tutomato.commerce.domain.cart

import com.tutomato.commerce.support.domain.CartDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import javax.management.InstanceAlreadyExistsException

class CartTest {


    private val 장바구니상품고유번호 = 99L
    private val 장바구니상품옵션고유번호 = 999L
    private val 새장바구니상품고유번호 = 1000L
    private val 새장바구니상품옵션고유번호 = 9000L
    private lateinit var 장바구니: Cart
    private lateinit var 기등록장바구니상품: CartItem
    private lateinit var 미등록장바구니상품: CartItem


    @Test
    fun `상품과 옵션 고유번호에 해당하는 장바구니 상품 조회`() {
        //when
        val 장바구니상품 = 장바구니.getItemByIds(장바구니상품고유번호, 장바구니상품옵션고유번호)!!

        //then
        assertThat(장바구니상품).isNotNull
        assertThat(장바구니상품.productId).isEqualTo(장바구니상품고유번호)
        assertThat(장바구니상품.optionId).isEqualTo(장바구니상품옵션고유번호)
    }


    @Nested
    @DisplayName("장바구니 상품 추가")
    inner class ItemInsertTest {

        @Test
        fun `동일한 옵션이 존재하지 않을 경우 상품 추가`() {
            //given
            val 장바구니상품수량 = 장바구니.cartItems.items.size

            //when
            장바구니.addItem(미등록장바구니상품)

            //when
            assertThat(장바구니.cartItems.items.size).isEqualTo(장바구니상품수량 + 1)
            assertThat(장바구니.cartItems.exist(미등록장바구니상품)).isTrue
        }

        @Test
        fun `동일한 옵션의 상품이 존재 할 경우 InstanceAlreadyExistsException 오류 발생`() {
            //when then
            assertThrows<InstanceAlreadyExistsException> { 장바구니.addItem(기등록장바구니상품) }
            assertThat(장바구니.cartItems.exist(기등록장바구니상품)).isTrue
        }
    }


    @Nested
    @DisplayName("장바구니 상품 삭제")
    inner class ItemRemoveTest {

        @Test
        fun `동일한 옵션의 상품이 이미 추가되어있을 경우 상품 제거`() {
            //when
            장바구니.removeItem(기등록장바구니상품)

            //then
            assertThat(장바구니.cartItems.exist(기등록장바구니상품)).isFalse
        }

        @Test
        fun `동일한 옵션이 존재하지 않을 경우 IllegalArgumentException 오류 발생`() {
            assertThrows<IllegalArgumentException> { 장바구니.removeItem(미등록장바구니상품) }
        }
    }


    @BeforeEach
    fun setUp() {
        장바구니 = CartDomainSupport.fixture(
            id = 10L,
            userId = 1L,
        )

        기등록장바구니상품 = CartDomainSupport.itemFixture(
            id = 1L,
            productId = 장바구니상품고유번호,
            optionId = 장바구니상품옵션고유번호,
            quantity = 10,
            cart = 장바구니,)

        미등록장바구니상품 = CartDomainSupport
            .itemFixture(
                productId = 새장바구니상품고유번호,
                optionId = 새장바구니상품옵션고유번호,
                quantity = 10,
                cart = 장바구니
            )

        장바구니.addItem(기등록장바구니상품)
    }
}
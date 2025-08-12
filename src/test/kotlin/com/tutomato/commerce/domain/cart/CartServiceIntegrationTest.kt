package com.tutomato.commerce.domain.cart

import jakarta.persistence.NoResultException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
class CartServiceIntegrationTest(
    @Autowired private val cartService: CartService,
    @Autowired private val cartRepository: CartRepository,
) {


    @Nested
    @DisplayName("사용자 고유번호 장바구니 목록 조회")
    inner class findbyuseridtest {

        @Test
        fun `존재할 경우 정상 조회`() {
            //given
            val 사용자고유번호 = 10L
            val cart = Cart(1L, 사용자고유번호)
            cartRepository.save(cart)

            //when
            val userCart = cartService.findByUserId(사용자고유번호)

            //then
            assertThat(userCart).isNotNull
        }

        @Test
        fun `존재하지 않을 경우 오류 발생`() {
            //given
            val 없는사용자고유번호 = 99L

            //when then
            assertThrows<NoResultException> { cartService.findByUserId(없는사용자고유번호) }
        }
    }
}
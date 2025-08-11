package com.tutomato.commerce.domain.order

import com.tutomato.commerce.support.domain.OrderDomainSupport
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest {


    private lateinit var orderService: OrderService
    private lateinit var orderRepository: OrderRepository

    @Test
    fun `사용자 주문 목록 중 미결제 주문이 존재할 경우 return true`() {
        //given
        val userId = 1L
        val orders = listOf(
            OrderDomainSupport.fixture(orderStatus = OrderStatus.PAID),
            OrderDomainSupport.fixture(orderStatus = OrderStatus.PENDING),
        )
        every { orderRepository.findByUserId(userId) }.returns(orders)

        //when
        assertThat(orderService.existsUnpaidOrderByUserId(userId)).isTrue()
    }

    @Test
    fun `미결제 주문이 존재하지 않을 경우 return false`() {
        //given
        val userId = 1L
        val orders = listOf(
            OrderDomainSupport.fixture(orderStatus = OrderStatus.PAID),
            OrderDomainSupport.fixture(orderStatus = OrderStatus.CANCELED),
        )
        every { orderRepository.findByUserId(userId) }.returns(orders)

        //when
        assertThat(orderService.existsUnpaidOrderByUserId(userId)).isFalse()
    }

    @BeforeEach
    fun init() {
        orderRepository = mockk()
        orderService = OrderService(orderRepository)
    }
}
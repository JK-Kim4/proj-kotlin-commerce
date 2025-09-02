package com.tutomato.commerce.domain.order

import com.tutomato.commerce.domain.user.User
import com.tutomato.commerce.domain.user.UserRepository
import com.tutomato.commerce.support.annotation.SupportSql
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
@Testcontainers
@SupportSql
class OrderServiceIntegrationTest(
    @Autowired private val orderService: OrderService,
    @Autowired private val userRepository: UserRepository,
) {

    lateinit var 사용자1: User

    @BeforeEach
    fun init() {
        사용자1 = User(name = "test user")
        userRepository.save(사용자1)
        userRepository.flush()
    }

    @Test
    fun `주문을 저장한다`() {
        //given
        val command = OrderCommand.OrderSaveCommand(
            userId = 사용자1.id,
            snapshots = listOf(
                OrderCommand.OrderProductSnapshot(
                    productId = 1L,
                    optionId = 10L,
                    price = BigDecimal.valueOf(5000),
                    quantity = 5
                ),
                OrderCommand.OrderProductSnapshot(
                    productId = 1L,
                    optionId = 11L,
                    price = BigDecimal.valueOf(5000),
                    quantity = 5
                ),
            )
        )

        //when
        val order = orderService.save(command)


        //then
        assertThat(order.id).isGreaterThan(0L)
        assertThat(order.orderLines.size()).isEqualTo(2)
        assertThat(order.orderLines.orderLines.all { it.order.id ==  order.id }).isTrue()
        assertThat(order.orderStatus == OrderStatus.CREATED).isTrue()
    }

    @Test
    fun `조회 기간내 결제 완료 주문 목록을 조회 - 3일`() {
        //given
        val criteria = OrderCriteria.PaidOrders(
            period = OrderPeriod.THREE_DAYS,
            calculatedAt = LocalDateTime.of(2025, 1, 4, 0, 0, 0),
        )

        //when
        val orders = orderService.findPaidOrdersByCriteria(criteria)

        //then
        assertThat(orders.size).isEqualTo(1)
        assertThat(orders.get(0).orderStatus).isEqualTo(OrderStatus.PAID)
    }

    @Test
    fun `조회 기간내 결제 완료 주문 목록을 조회 - 1달`() {
        //given
        val criteria = OrderCriteria.PaidOrders(
            period = OrderPeriod.ONE_MONTH,
            calculatedAt = LocalDateTime.of(2025, 1, 4, 0, 0, 0),
        )

        //when
        val orders = orderService.findPaidOrdersByCriteria(criteria)

        //then
        assertThat(orders.size).isEqualTo(2)
        orders.forEach { order -> assertThat(order.orderStatus).isEqualTo(OrderStatus.PAID) }
    }
}
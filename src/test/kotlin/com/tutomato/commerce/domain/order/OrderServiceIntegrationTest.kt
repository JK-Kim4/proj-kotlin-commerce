package com.tutomato.commerce.domain.order

import com.tutomato.commerce.domain.user.User
import com.tutomato.commerce.domain.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@SpringBootTest
@Testcontainers
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
}
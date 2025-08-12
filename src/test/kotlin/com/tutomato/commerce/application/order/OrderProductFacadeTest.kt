package com.tutomato.commerce.application.order

import com.tutomato.commerce.domain.order.OrderRepository
import com.tutomato.commerce.domain.order.OrderStatus
import com.tutomato.commerce.domain.order.dto.OrderProductSnapshot
import com.tutomato.commerce.domain.order.dto.OrderSaveCommand
import com.tutomato.commerce.domain.product.Options
import com.tutomato.commerce.domain.product.Product
import com.tutomato.commerce.domain.product.ProductRepository
import com.tutomato.commerce.domain.product.Stock
import com.tutomato.commerce.domain.user.User
import com.tutomato.commerce.domain.user.UserRepository
import com.tutomato.commerce.infrastructure.product.OptionJpaRepository
import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.OrderDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.startup.StartupEndpoint
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class OrderProductFacadeTest(
    @Autowired val orderProductFacade: OrderProductFacade,
    @Autowired val orderRepository: OrderRepository,
    @Autowired val productRepository: ProductRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val optionJpaRepository: OptionJpaRepository,
) {

    lateinit var 구매자: User
    lateinit var 판매상픔: Product
    lateinit var command: OrderSaveCommand
    var 상품옵션 = OptionDomainSupport.fixture(stock = 10)


    @Test
    fun `미결제 주문이 존재할 경우 주문을 생성할 수 없다`() {
        //given
        val 미결제주문 = OrderDomainSupport.fixture(userId = 구매자.id, orderStatus = OrderStatus.PENDING)
        orderRepository.save(미결제주문)

        //when then
        assertThrows<IllegalArgumentException> { orderProductFacade.create(command) }
    }

    @Test
    fun `주문이 정상적으로 생성되면 주문 수량만큼 상품 재고가 차감된다`() {
        //when
        val saveResult = orderProductFacade.create(command)
        val option = optionJpaRepository.findById(상품옵션.id).get()
        val order = orderRepository.findById(saveResult.id)!!

        //then
        assertThat(option.stock == Stock(9)).isTrue()
        assertThat(order.orderStatus).isEqualTo(OrderStatus.PENDING)
    }


    @BeforeEach
    fun init() {
        구매자 = User("tester")
        판매상픔 = ProductDomainSupport.fixture(
            options = Options(listOf(상품옵션))
        )

        userRepository.save(구매자)
        productRepository.save(판매상픔)

        command = OrderSaveCommand(
            userId = 구매자.id,
            snapshots = listOf(
                OrderProductSnapshot(
                    productId = 판매상픔.id,
                    optionId = 상품옵션.id,
                    price = 판매상픔.price.value,
                    quantity = 1,
                )
            )
        )
    }

    @AfterEach
    fun clean() {
        orderRepository.deleteAll()
    }
}
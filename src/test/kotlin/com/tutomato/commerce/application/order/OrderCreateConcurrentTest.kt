package com.tutomato.commerce.application.order

import com.tutomato.commerce.domain.order.OrderCommand
import com.tutomato.commerce.domain.order.OrderRepository
import com.tutomato.commerce.domain.product.Options
import com.tutomato.commerce.domain.product.Product
import com.tutomato.commerce.domain.product.ProductRepository
import com.tutomato.commerce.domain.user.User
import com.tutomato.commerce.domain.user.UserRepository
import com.tutomato.commerce.infrastructure.product.OptionJpaRepository
import com.tutomato.commerce.support.domain.OptionDomainSupport
import com.tutomato.commerce.support.domain.ProductDomainSupport
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import kotlin.math.min

@SpringBootTest
@Testcontainers
class OrderCreateConcurrentTest(
    @Autowired val orderProductFacade: OrderProductFacade,
    @Autowired val orderRepository: OrderRepository,
    @Autowired val productRepository: ProductRepository,
    @Autowired val userRepository: UserRepository,
    @Autowired val optionJpaRepository: OptionJpaRepository,
) {

    lateinit var 구매자: User
    lateinit var 판매상픔: Product
    var 상품옵션 = OptionDomainSupport.fixture(stock = 10)


    @BeforeEach
    fun init() {
        구매자 = User(name = "tester")
        판매상픔 = ProductDomainSupport.fixture(
            options = Options(listOf(상품옵션))
        )

        userRepository.save(구매자)
        productRepository.save(판매상픔)
    }

    @Test
    fun `동시 주문 생성 재고 차감 테스트`() {
        runBlocking {
            val snapshots = listOf(
                OrderCommand.OrderProductSnapshot(
                    productId = 판매상픔.id,
                    optionId = 상품옵션.id,
                    price = 판매상픔.price.value,
                    quantity = 1,
                )
            )

            val commands = listOf(
                OrderCommand.OrderSaveCommand(userId = 1L, snapshots = snapshots),
                OrderCommand.OrderSaveCommand(userId = 2L, snapshots = snapshots),
                OrderCommand.OrderSaveCommand(userId = 3L, snapshots = snapshots),
                OrderCommand.OrderSaveCommand(userId = 4L, snapshots = snapshots),
                OrderCommand.OrderSaveCommand(userId = 5L, snapshots = snapshots),
            )

            // 2) 동시 출발 세팅
            val parallelism = min(5, commands.size)
            val dispatcher = Dispatchers.IO.limitedParallelism(parallelism)
            val startGate = CompletableDeferred<Unit>()

            val jobs = commands.map { command ->
                async(dispatcher) {
                    startGate.await()
                    command to runCatching { orderProductFacade.order(command) }
                }
            }

            startGate.complete(Unit)            // 동시에 출발
            jobs.awaitAll()

            val option = optionJpaRepository.findById(상품옵션.id).get()

            //검증
            assertThat(option.stock.stock).isEqualTo(5)
        }
    }
}
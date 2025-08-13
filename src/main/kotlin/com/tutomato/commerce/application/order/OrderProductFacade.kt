package com.tutomato.commerce.application.order

import com.tutomato.commerce.domain.order.OrderCommand
import com.tutomato.commerce.domain.order.OrderCreatedEvent
import com.tutomato.commerce.domain.order.OrderResult
import com.tutomato.commerce.domain.order.OrderService
import com.tutomato.commerce.domain.product.ProductService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class OrderProductFacade(
    private val publisher: ApplicationEventPublisher,
    private val orderService: OrderService,
    private val productService: ProductService,
) {

    fun create(command: OrderCommand.OrderSaveCommand): OrderResult.OrderSaveResult {
        //사용자 미결제 주문 여부 확인
        require(!orderService.existsUnpaidOrderByUserId(command.userId)) {
            "미결제 주문이 존재합니다."
        }

        //상품 재고 선점 요청 -> 현재 정책상 상품의 재고는 음수로 내려갈 수 없어 주문 생성과 동일한 트랜잭션에서 재고 차감 진행
        command.snapshots.forEach {
            snapshot -> productService
                .findOptionByOptionIdWithPessimisticLock(snapshot.optionId)
                .decreaseStock(snapshot.quantity)
        }

        //주문 생성
        val order = orderService.save(command)

        //상태 변경
        order.pending()

        //주문 이벤트 발행
        publisher.publishEvent(OrderCreatedEvent.from(order))

        return OrderResult.OrderSaveResult.from(order)
    }
}
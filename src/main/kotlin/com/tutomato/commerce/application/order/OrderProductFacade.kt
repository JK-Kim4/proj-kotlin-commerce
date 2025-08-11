package com.tutomato.commerce.application.order

import com.tutomato.commerce.domain.order.OrderSave
import com.tutomato.commerce.domain.order.OrderSaveResult
import com.tutomato.commerce.domain.order.OrderService
import com.tutomato.commerce.domain.product.ProductService
import org.springframework.stereotype.Component

@Component
class OrderProductFacade(
    private val orderService: OrderService,
    private val productService: ProductService,
) {

    fun create(command: OrderSave): OrderSaveResult {
        //사용자 미결제 주문 여부 확인
        if (orderService.existsUnpaidOrderByUserId(command.userId)) {
            throw IllegalArgumentException("미결제 주문이 존재합니다.")
        }

        //TODO 상품 재고 선점 요청

        //주문 생성
        val order = orderService.save(command)


        //TODO 주문 이벤트 발행


        return OrderSaveResult.from(order)
    }
}
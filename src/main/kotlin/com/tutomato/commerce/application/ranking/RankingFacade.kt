package com.tutomato.commerce.application.ranking

import com.tutomato.commerce.domain.order.OrderCriteria
import com.tutomato.commerce.domain.order.OrderService
import com.tutomato.commerce.domain.product.ProductService
import com.tutomato.commerce.domain.ranking.RankingCriteria
import com.tutomato.commerce.domain.ranking.RankingResult
import com.tutomato.commerce.domain.ranking.RankingService
import org.springframework.stereotype.Component

@Component
class RankingFacade(
    private val orderService: OrderService,
    private val productService: ProductService,
) {

    fun getRanking(criteria: RankingCriteria.Ranking): RankingResult.Rankings {
        //결제 완료 주문 목록 조회
        val orders = orderService.findPaidOrdersByCriteria(OrderCriteria.PaidOrders(criteria.period, criteria.calculatedAt))

        //결제 완료 주문 상품 목록 조회
        val orderLines = orderService.findOrderLinesByOrderIds(
            orders.map { order -> order.id }.toSet()
        )

        //상품 목록 조회
        val products = productService.findByIds(
            orderLines.map { orderLine ->
                orderLine.snapshot.productId
            }.toSet()
        )

        //판매 순위 목록 생성 반환
        return RankingService.generate(orderLines, products)
    }
}
package com.tutomato.commerce.domain.ranking

import com.tutomato.commerce.domain.order.OrderLine
import com.tutomato.commerce.domain.product.Product


class RankingService {

    companion object {

        fun generate(orderLines: List<OrderLine>, products: List<Product>): RankingResult.Rankings {
            val rankings = orderLines
                .groupBy { it.snapshot.productId }
                .map { (_, lines) ->
                    val snapshot = lines.first().snapshot
                    val totalOrderCount = lines.sumOf { it.snapshot.quantity }
                    RankingResult.RankingProduct.of(
                        productId = snapshot.productId,
                        name = products.first { it.id == snapshot.productId }.info.name,
                        price = products.first { it.id == snapshot.productId }.price,
                        totalOrderCount = totalOrderCount,
                    )
                }.sortedByDescending { it.totalOrderCount }
                .toList()


            return RankingResult.Rankings(rankings)
        }
    }

}
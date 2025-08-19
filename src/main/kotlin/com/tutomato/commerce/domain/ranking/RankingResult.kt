package com.tutomato.commerce.domain.ranking

import com.tutomato.commerce.common.model.Money

class RankingResult {

    data class Rankings(
        val ranking: List<RankingProduct>,
    ) {

        companion object {
            fun from(rankings: List<RankingProduct>): RankingResult.Rankings {
                return RankingResult.Rankings(rankings)
            }
        }
    }

    data class RankingProduct(
        val productId: Long,
        val productName: String,
        val price: Money,
        var totalOrderCount: Int = 0,
    ) {
        companion object {
            fun of(productId: Long, name: String, price: Money, totalOrderCount: Int): RankingProduct {
                return RankingProduct(
                    productId = productId,
                    productName = name,
                    price = price,
                    totalOrderCount = totalOrderCount,
                )
            }
        }
    }
}
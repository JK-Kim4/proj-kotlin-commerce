package com.tutomato.commerce.domain.ranking

import com.tutomato.commerce.domain.order.OrderPeriod
import java.time.LocalDateTime

class RankingCriteria {

    data class Ranking(
        val period: OrderPeriod,
        val calculatedAt: LocalDateTime = LocalDateTime.now(),
    )
}
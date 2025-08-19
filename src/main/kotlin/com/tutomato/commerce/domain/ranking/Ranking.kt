package com.tutomato.commerce.domain.ranking

import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.order.OrderPeriod
import java.time.LocalDateTime

data class Ranking(
    val productId: Long,
    val productName: String,
    val price: Money,
    var totalOrderCount: Int = 0,
    val orderPeriod: OrderPeriod,
    val calculatedAt: LocalDateTime,
    ) {

    var optionReports: OptionReports = OptionReports()

}
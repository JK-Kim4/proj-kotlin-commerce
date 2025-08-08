package com.tutomato.commerce.domain.order

import jakarta.persistence.Embeddable

@Embeddable
class OrderLines(
    val orderLines: List<OrderLine> = listOf(),
) {

}
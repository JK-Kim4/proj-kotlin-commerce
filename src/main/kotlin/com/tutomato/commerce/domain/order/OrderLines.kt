package com.tutomato.commerce.domain.order

import jakarta.persistence.CascadeType
import jakarta.persistence.OneToMany

class OrderLines(

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderLines: List<OrderLine> = listOf(),
) {
}
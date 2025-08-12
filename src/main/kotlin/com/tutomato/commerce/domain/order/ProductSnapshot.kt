package com.tutomato.commerce.domain.order

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.Embeddable

@Embeddable
data class ProductSnapshot(
    val productId: Long,

    val optionId: Long,

    val price: Money,

    val quantity: Int,
)

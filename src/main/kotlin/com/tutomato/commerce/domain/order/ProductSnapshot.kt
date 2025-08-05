package com.tutomato.commerce.domain.order

import jakarta.persistence.Embeddable

@Embeddable
data class ProductSnapshot(
    val productId: Long,

)

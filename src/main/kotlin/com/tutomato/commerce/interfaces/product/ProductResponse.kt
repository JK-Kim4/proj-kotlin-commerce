package com.tutomato.commerce.interfaces.product

import java.math.BigDecimal

class ProductResponse (
    val id: Long,
    val name: String,
    val description: String,
    val price : BigDecimal,
    val stock: Int
)

class ProductResponses (
    val products: List<ProductResponse>
)

class PopularProductResponse (
    val id: Long,
    val name: String,
    val popularityRank: Int,
    val salesCount: Int
)

class PopularProductResponses (
    val products: List<PopularProductResponse>
)
package com.tutomato.commerce.interfaces.product

import java.math.BigDecimal

class ProductResponse {

    class Product (val id: Long, val name: String, val description: String, val price : BigDecimal, val stock: Int)

    class Products (val products: List<ProductResponse.Product>)

    class Popular (val id: Long, val name: String, val popularityRank: Int, val salesCount: Int)

    class Populars (val products: List<ProductResponse.Popular>)

}
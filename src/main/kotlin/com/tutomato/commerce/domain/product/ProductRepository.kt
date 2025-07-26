package com.tutomato.commerce.domain.product

import java.util.*


interface ProductRepository {

    fun save(product: Product)

    fun findById(productId : Long) : Optional<Product>

    fun findAll() : List<Product>

}
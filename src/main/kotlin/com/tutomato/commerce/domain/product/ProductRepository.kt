package com.tutomato.commerce.domain.product


interface ProductRepository {

    fun save(product: Product)

    fun findById(productId : Long) : Product

    fun findAll() : List<Product>

    fun findOptionById(optionId : Long) : Option

    fun save(option: Option)

}
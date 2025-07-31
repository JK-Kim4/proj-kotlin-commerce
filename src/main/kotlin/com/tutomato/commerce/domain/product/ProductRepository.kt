package com.tutomato.commerce.domain.product


interface ProductRepository {

    fun save(product: Product): Product

    fun save(option: Option) : Option

    fun findById(productId : Long) : Product?

    fun findAll() : List<Product>

    fun findOptionById(optionId : Long) : Option?

}
package com.tutomato.commerce.domain.product


interface ProductRepository {

    fun save(product: Product): Product

    fun saveAll(products: List<Product>)

    fun flush()

    fun save(option: Option) : Option

    fun findById(productId : Long) : Product?

    fun findAll() : List<Product>

    fun findOptionById(optionId : Long) : Option?

    fun findOptionByOptionIdWithPessimisticLock(optionId : Long) : Option?

    fun findAllOptionsByIdInForUpdate(optionIds: Set<Long>): List<Option>

}
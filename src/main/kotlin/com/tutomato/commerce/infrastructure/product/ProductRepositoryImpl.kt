package com.tutomato.commerce.infrastructure.product

import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl {

    private lateinit var productJpaRepository: ProductJpaRepository

}
package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long> {
}
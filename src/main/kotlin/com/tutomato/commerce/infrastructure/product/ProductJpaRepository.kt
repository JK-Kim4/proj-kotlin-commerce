package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductJpaRepository : JpaRepository<Product, Long> {

    @Query("select p from Product p where p.id in :productIds")
    fun findByIdsIn(@Param("productIds") productIds: Set<Long>): List<Product>

}
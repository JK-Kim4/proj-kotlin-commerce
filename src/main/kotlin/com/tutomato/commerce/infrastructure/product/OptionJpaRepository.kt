package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import org.springframework.data.jpa.repository.JpaRepository

interface OptionJpaRepository : JpaRepository<Option, Long> {
}
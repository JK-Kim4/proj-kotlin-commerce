package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OptionJpaRepository : JpaRepository<Option, Long> {

    fun findByProductId(productId: Long): List<Option>

    fun findByProductIdIn(productIds: Set<Long>): List<Option>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Option o where o.id = :optionId")
    fun findOptionByOptionIdWithPessimisticLock(@Param("optionId") optionId: Long): Option?

}
package com.tutomato.commerce.coupon.adapter.out.jpa

import com.tutomato.commerce.coupon.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponPersistenceAdapter: JpaRepository<Coupon, Long> {
}
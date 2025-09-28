package com.tutomato.commerce.coupon.adapter.`in`.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/coupons")
class CreateCouponController {

    @PostMapping
    fun create(
        @RequestBody request: CreateCouponRequest
    ): ResponseEntity<CreateCouponResponse> {
        TODO("신규 쿠폰 등록")
    }

}
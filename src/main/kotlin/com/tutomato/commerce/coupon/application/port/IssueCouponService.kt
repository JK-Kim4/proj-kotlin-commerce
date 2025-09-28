package com.tutomato.commerce.coupon.application.port

import com.tutomato.commerce.coupon.application.dto.CouponCommand
import com.tutomato.commerce.coupon.application.dto.CouponResult
import com.tutomato.commerce.coupon.application.port.`in`.IssueCouponUseCase

/** 쿠폰 발급 */
class IssueCouponService(
): IssueCouponUseCase {

    override fun execute(command: CouponCommand.Issue): CouponResult.Issue {
        TODO("Not yet implemented")
        // 1. 발급 요청 쿠폰 로드

        // 2. 쿠폰 유효성 검증 (재고수량 & 만료일)

        // 3.
    }
}
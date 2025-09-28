package com.tutomato.commerce.coupon.application.port.`in`

import com.tutomato.commerce.coupon.application.dto.CouponCommand
import com.tutomato.commerce.coupon.application.dto.CouponResult

interface IssueCouponUseCase {

    fun execute(command: CouponCommand.Issue): CouponResult.Issue
}
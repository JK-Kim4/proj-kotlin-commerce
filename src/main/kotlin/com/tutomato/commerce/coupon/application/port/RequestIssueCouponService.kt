package com.tutomato.commerce.coupon.application.port

import com.tutomato.commerce.coupon.application.dto.CouponCommand
import com.tutomato.commerce.coupon.application.port.out.LoadCouponPort
import com.tutomato.commerce.coupon.application.port.out.RequestIssueCouponPort
import org.springframework.stereotype.Service

@Service
class RequestIssueCouponService(
    private val loadCouponPort: LoadCouponPort,
    private val requestIssueCouponPort: RequestIssueCouponPort
) {

    fun execute(command: CouponCommand.Request){
        // 1. 쿠폰 정보 조회
        val coupon = loadCouponPort.findById(command.couponId)
            ?: throw IllegalArgumentException("coupon with id ${command.couponId} not found")

        // 2. 쿠폰 유효성 검증
        coupon.issuableValidation(command.requestedDate)

        // 3. 발급 요청 사용자 정보 검증 (기발급 요청 / 쿠폰 발급 여부)
        if(requestIssueCouponPort.isAlreadyRequested(command.couponId, command.userId)
            || loadCouponPort.isAlreadyIssued(command.couponId, command.userId)) {
            throw IllegalArgumentException("coupon ${command.couponId} already issued")
        }

        // 4. 쿠폰 발급 요청
        requestIssueCouponPort.request(command.couponId, command.userId, command.requestedDate)
    }
}

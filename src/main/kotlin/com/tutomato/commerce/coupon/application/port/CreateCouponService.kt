package com.tutomato.commerce.coupon.application.port

import com.tutomato.commerce.coupon.application.dto.CouponCommand
import com.tutomato.commerce.coupon.application.dto.CouponResult
import com.tutomato.commerce.coupon.application.port.out.CouponPersistencePort
import com.tutomato.commerce.coupon.application.port.out.LoadCouponPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** 신규 쿠폰 생성 */
@Service
@Transactional
class CreateCouponService(
    private val loadCouponPort: LoadCouponPort,
    private val couponPersistencePort: CouponPersistencePort
) {

    fun execute(command: CouponCommand.Create): CouponResult.Create {
        loadCouponPort.findByName(command.name)?.let {
            throw IllegalArgumentException("Coupon already exists")
        }

        val coupon = couponPersistencePort.save(command.toEntity())

        return CouponResult.Create.from(coupon)
    }
}
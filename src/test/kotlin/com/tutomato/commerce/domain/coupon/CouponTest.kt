package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant

class CouponTest {

    @Test
    @DisplayName("쿠폰 정보를 생성하여 할인 금액을 계산한다.")
    fun coupon_discount_calculate_test() {
        //given
        val price = Money(BigDecimal.TEN)
        val coupon = Coupon(
            id = 10,
            type = CouponType.RATE,
            discountValue = BigDecimal.valueOf(0.1),
            amount = 100,
            expiredAt = Instant.now()
        )

        //when
        val discountAmount = coupon.getDiscountAmount(price)
        val discountResult = coupon.calculateFinalPrice(price)

        //then
        assertThat(discountAmount.value).isEqualTo(BigDecimal.valueOf(1))
        assertThat(discountResult.value).isEqualTo(BigDecimal.valueOf(9))
    }

    @Test
    @DisplayName("할인 금액 적용 결과가 음수일 경우 0을 리턴")
    fun return_zero_when_sub() {
        //given
        val price = Money(BigDecimal.TEN)
        val coupon = Coupon(
            id = 10,
            type = CouponType.AMOUNT,
            discountValue = BigDecimal.valueOf(1000),
            amount = 100,
            expiredAt = Instant.now()
        )

        //when
        val discountAmount = coupon.getDiscountAmount(price)
        val discountResult = coupon.calculateFinalPrice(price)

        //then
        assertThat(discountAmount.value).isEqualTo(BigDecimal.valueOf(1000))
        assertThat(discountResult.value).isEqualTo(BigDecimal.ZERO)
    }
}
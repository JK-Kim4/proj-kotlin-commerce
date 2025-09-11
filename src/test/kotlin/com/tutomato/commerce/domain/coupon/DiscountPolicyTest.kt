package com.tutomato.commerce.domain.coupon

import com.tutomato.commerce.common.model.Money
import org.assertj.core.api.Assertions.assertThat


import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.jvm.java

class DiscountPolicyTest {

    private val PRICE = Money(BigDecimal.valueOf(50_000))

    @Test
    @DisplayName("정량 할인 정책 할인 적용 테스트")
    fun amount_discount_policy_test() {
        //given
        val discountAmount = BigDecimal.valueOf(30_000.00)
        val policy: DiscountPolicy = AmountDiscountPolicy(discountAmount)

        //when
        val discountResultPrice = policy.calculateDiscountAmount(PRICE)

        //then
        assertThat(discountResultPrice.value).isEqualByComparingTo(BigDecimal.valueOf(20_000))
    }

    @Test
    @DisplayName("정률 할인 정책 할인 적용 테스트")
    fun rate_discount_policy_test() {
        //given
        val discountRate = BigDecimal.valueOf(0.1)  // 10%할인
        val policy: DiscountPolicy = RateDiscountPolicy(discountRate)

        //when
        val discountAmount = policy.getDiscountAmount(PRICE)
        val discountResultPrice = policy.calculateDiscountAmount(PRICE)


        //then
        assertThat(discountAmount.value).isEqualByComparingTo(BigDecimal.valueOf(5000))
        assertThat(discountResultPrice.value).isEqualByComparingTo(BigDecimal.valueOf(45_000))
    }

    @Test
    @DisplayName("coupon type정보로 할인 정책 객체 생성")
    fun create_policy_test() {
        //when
        val amountPolicy = DiscountPolicyFactory.from(CouponType.AMOUNT, BigDecimal.valueOf(50_000))
        val ratePolicy = DiscountPolicyFactory.from(CouponType.RATE, BigDecimal.valueOf(0.2))

        //then
        assertThat(amountPolicy).isInstanceOf(DiscountPolicy::class.java)
        assertThat(ratePolicy).isInstanceOf(DiscountPolicy::class.java)
        assertThat(amountPolicy).isInstanceOf(AmountDiscountPolicy::class.java)
        assertThat(ratePolicy).isInstanceOf(RateDiscountPolicy::class.java)
    }
}
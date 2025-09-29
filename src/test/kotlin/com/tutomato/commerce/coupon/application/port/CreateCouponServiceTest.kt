package com.tutomato.commerce.coupon.application.port

import com.tutomato.commerce.coupon.application.dto.CouponCommand
import com.tutomato.commerce.coupon.application.port.out.PersisCouponPort
import com.tutomato.commerce.coupon.application.port.out.LoadCouponPort
import com.tutomato.commerce.coupon.domain.Coupon
import com.tutomato.commerce.coupon.domain.CouponType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset

class CreateCouponServiceTest {

    private val 등록테스트쿠폰이름 = "TEST_COUPON"
    private val 테스트쿠폰 =
        Coupon.create(
            name = 등록테스트쿠폰이름,
            type = CouponType.RATE,
            discountValue = BigDecimal.valueOf(0.1),
            amount = 100,
            expiredAt = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1)
        )

    private lateinit var createCouponService: CreateCouponService
    private lateinit var loadCouponPort: LoadCouponPort
    private lateinit var couponPersistencePort: PersisCouponPort

    @Test
    @DisplayName("동일한 이름의 쿠폰이 이미 존재할 경우 쿠폰 생성시 IllegalArgumentException 예외가 발생하고 쿠폰이 저장되지 않는다.")
    fun create_coupon_fail() {
        //given
        val command = CouponCommand.Create(
            name = 등록테스트쿠폰이름,
            type = CouponType.RATE,
            discountValue = BigDecimal.valueOf(0.1),
            amount = 100,
            expiredAt = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1)
        )

        //when then
        assertThatThrownBy { createCouponService.execute(command) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("Coupon already exists")
        verify(exactly = 0) {
            couponPersistencePort.save(any<Coupon>())
        }
    }

    @BeforeEach
    fun init() {
        loadCouponPort = mockk()
        couponPersistencePort = mockk()

        every { loadCouponPort.findByName(등록테스트쿠폰이름) } returns 테스트쿠폰

        createCouponService = CreateCouponService(
            loadCouponPort = loadCouponPort,
            couponPersistencePort = couponPersistencePort
        )

    }

}
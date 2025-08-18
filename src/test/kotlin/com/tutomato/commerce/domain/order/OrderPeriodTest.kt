package com.tutomato.commerce.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class OrderPeriodTest {

    @Test
    fun `전달받은 LocalDateTime에서 Period 만큼 이전의 LocalDateTime일 반환한다`() {
        //given
        val targetDateTime = LocalDateTime.of(2020, 1, 1, 0, 0)
        val threeDaysBeforePeriod = OrderPeriod.THREE_DAYS

        //when
        val threeDaysAfterPeriod = threeDaysBeforePeriod.getStartDateTime(targetDateTime)

        //then
        assertThat(threeDaysAfterPeriod).isEqualTo(targetDateTime.minus(3, ChronoUnit.DAYS))
    }
}
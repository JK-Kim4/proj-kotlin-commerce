package com.tutomato.commerce.domain.order

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

enum class OrderPeriod(
    val durationInHours: Number,
    val label: String
) {

    REALTIME(0.5, "실시간"), //실시간 상품의 경우 조회 시점으로부터 30분간 판매 내역
    THREE_DAYS(72, "3일"),
    ONE_WEEK(168, "일주일"),
    ONE_MONTH(1800, "한달");

    fun getStartDateTime(targetDateTime: LocalDateTime): LocalDateTime {
        val hours = durationInHours.toDouble()
        val minutes = (hours * 60).toLong()
        return targetDateTime.minus(minutes, ChronoUnit.MINUTES)
    }

}
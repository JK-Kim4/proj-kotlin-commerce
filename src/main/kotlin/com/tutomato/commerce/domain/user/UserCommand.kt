package com.tutomato.commerce.domain.user

import java.math.BigDecimal

class UserCommand {

    data class DeductPoint(
        val userId: Long,
        val amount: BigDecimal,
    )
}
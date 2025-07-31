package com.tutomato.commerce.domain.cart

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class CartItem(
    @Id var id: Long = 0,
    val productId: Long,
    val optionId: Long,
    var amount: Int,
    @ManyToOne(fetch = FetchType.LAZY) val cart : Cart,
) {

    fun updateAmount(amount: Int) {
        this.amount = amount
    }

}
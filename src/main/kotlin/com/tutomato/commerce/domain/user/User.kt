package com.tutomato.commerce.domain.user

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*


@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,

    @Embedded
    var balance: Balance = Balance()
) {
    @Version
    var version: Long = 0

    init {
        require(name.isNotBlank()) { "User name cannot be blank" }
    }

    fun pay(amount: Money) {
        balance = balance.deduct(amount)
    }

}
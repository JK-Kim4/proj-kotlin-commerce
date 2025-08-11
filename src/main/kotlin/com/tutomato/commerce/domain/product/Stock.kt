package com.tutomato.commerce.domain.product

import jakarta.persistence.Embeddable

@Embeddable
class Stock(

    val stock: Int
) {

    fun decrease(amount : Int) : Stock{
        if(amount > stock) {
            throw IllegalArgumentException("Amount can't be greater than stock")
        }

        return Stock(stock - amount)
    }

    fun hasStock() : Boolean {
        return this.stock > 0
    }

    fun hasEnoughStock(stock: Int): Boolean {
        return this.stock >= stock
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Stock

        return stock == other.stock
    }

    override fun hashCode(): Int {
        return stock
    }


}

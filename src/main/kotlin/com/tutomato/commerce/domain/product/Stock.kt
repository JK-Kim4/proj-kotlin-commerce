package com.tutomato.commerce.domain.product

class Stock(

    val stock: Int

) {

    fun hasStock() : Boolean {
        return this.stock > 0
    }

    fun hasEnoughStock(stock: Int): Boolean {
        return this.stock > stock
    }

}

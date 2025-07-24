package com.tutomato.commerce.domain.product

class Option (


    val color: Color?,
    val size: Size?,
    val stock: Stock? = Stock(0),

) {

    fun matches(target: Option): Boolean {
        return (target.color == null || target.color == this.color) &&
                (target.size == null || target.size == this.size)
    }

    fun isEqualColor(color: Color) : Boolean {
        return this.color == color
    }

    fun isEqualSize(size: Size) : Boolean {
        return this.size == size
    }

}
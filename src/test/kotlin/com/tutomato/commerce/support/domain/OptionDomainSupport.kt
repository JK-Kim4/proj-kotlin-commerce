package com.tutomato.commerce.support.domain

import com.tutomato.commerce.domain.product.*

object OptionDomainSupport {

    fun fixture(
        id: Long = 0L,
        product: Product? = null,
        color: String = "#ffffff",
        size: String = "S",
        stock: Int = 10,
    ): Option {
        return Option(
            id = id,
            product = product,
            color = Color(color),
            size = Size.valueOf(size),
            stock = Stock(stock),
        )
    }

}
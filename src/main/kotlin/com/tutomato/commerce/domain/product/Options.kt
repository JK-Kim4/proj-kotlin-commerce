package com.tutomato.commerce.domain.product

class Options(
    val options: List<Option>,
) {

    constructor() : this(emptyList())

    fun setProduct(product: Product) {
        options.forEach { it.product = product }
    }

    fun size() : Int {
        return options.size
    }

    fun findOption(option: Option) : Option? {
        return options.find { it.id == option.id }
    }

    fun findOptionByColorAndSize(colorCode: String, size: String) : Option? {
        return options.find { it.color == Color(colorCode) && it.size == Size.valueOf(size) }
    }
}

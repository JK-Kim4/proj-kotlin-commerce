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


}

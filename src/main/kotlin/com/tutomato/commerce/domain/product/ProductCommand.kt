package com.tutomato.commerce.domain.product

import java.time.LocalDate

data class ProductSave(
    val name: String,
    val description: String,
    val publishedDate: LocalDate,
    val category: String,
    val options: List<OptionCommand>
) {
    fun toEntity() : Product {
        val options = this.options
            .map { command -> command.toEntity() }
            .toList()

        return Product(
            info = ProductInfo(
                name = this.name,
                description = this.description,
                publishedDate = this.publishedDate,),
            category = Category.valueOf(this.category),
            availableOptions = Options(options)
        )
    }
}

data class DecreaseStock(
    val productId: Long,
    val optionId : Long,
    val decreaseAmount: Int,
)

data class OptionCommand(
    val colorCode: String,
    val size: String,
    val stock: Int,
) {
    fun toEntity() : Option {
        return Option(
            color = Color(code = this.colorCode),
            size = Size.valueOf(this.size),
            stock = Stock(this.stock),
        )
    }
}
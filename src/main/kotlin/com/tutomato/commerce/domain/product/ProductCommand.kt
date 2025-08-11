package com.tutomato.commerce.domain.product

import com.tutomato.commerce.common.model.Money
import java.math.BigDecimal
import java.time.LocalDate

data class ProductSave(
    val name: String,
    val description: String,
    val publishedDate: LocalDate,
    val category: String,
    val options: List<OptionCommand>,
    val price: BigDecimal,
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
            availableOptions = Options(options),
            price = Money(this.price),
        )
    }
}

data class ProductOptionSave(
    val productId: Long,
    val colorCode: String,
    val size: String,
    val stock: Int,
) {
    fun toEntity(product: Product) : Option {
        return Option(
            product = product,
            color = Color(colorCode),
            size = Size.valueOf(size),
            stock = Stock(stock)
        )
    }
}

data class DecreaseStock(
    val productId: Long,
    val optionId : Long,
    val decreaseAmount: Int,
)

data class UpdateStatus(
    val productId: Long,
    val updateStatus : String,
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
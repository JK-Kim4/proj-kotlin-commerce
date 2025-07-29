package com.tutomato.commerce.domain.product

import java.math.BigDecimal
import java.time.LocalDate

class ProductResult (
    val id: Long,
    //val sellerId: Long,
    val name: String,
    val description: String,
    val publishedDate: LocalDate,
    val category: String,
    val saleStatus: String,
    val price: BigDecimal,
    val options: List<OptionResult>,
) {
    companion object {
        fun from(product: Product) : ProductResult {
            val options = product.availableOptions
                .options
                .map { option -> OptionResult.from(option) }
                .toList()

            return ProductResult(
                id = product.id,
                name = product.info.name,
                description = product.info.description,
                publishedDate = product.info.publishedDate,
                category = product.category.name,
                saleStatus = product.saleStatus.name,
                options = options,
                price = product.price.value
            )
        }
    }
}


class OptionResult(
    val id: Long,
    val colorCode: String,
    val size: String,
    val stock: Int,
) {
    companion object {
        fun from(option: Option): OptionResult {
            return OptionResult(
                id = option.id!!,
                colorCode = option.color!!.code,
                size = option.size!!.name,
                stock = option.stock!!.stock
            )
        }
    }
}

data class ProductResults (
    val products : List<ProductResult>
) {
    companion object {
        fun from(products: List<com.tutomato.commerce.domain.product.Product>) : ProductResults {
            //TODO
            return ProductResults(
                products.stream()
                    .map { product -> ProductResult.from(product) }
                    .toList()
            )
        }
    }
}
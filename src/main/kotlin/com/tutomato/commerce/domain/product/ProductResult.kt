package com.tutomato.commerce.domain.product

import java.math.BigDecimal
import java.time.LocalDate

class ProductResult {

    data class Products (
        val products : List<Product>
    ) {
        companion object {
            fun from(products: List<com.tutomato.commerce.domain.product.Product>) : Products {
                //TODO
                return Products(
            products.stream()
                    .map { product -> Product.from(product) }
                    .toList()
                )
            }
        }
    }

    data class Product (
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
            fun from(product: com.tutomato.commerce.domain.product.Product) : ProductResult.Product {
                val options = product.availableOptions!!
                    .options
                    .map { option -> OptionResult.from(option) }
                    .toList()

                return ProductResult.Product(
                    id = product.id,
                    name = product.info.name,
                    description = product.info.description,
                    publishedDate = product.info.publishedDate,
                    category = product.category.name,
                    saleStatus = product.saleStatus!!.name,
                    price = product.price.value,
                    options = options,
                )
            }
        }

    }

    data class OptionResult(
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
                    stock = option.stock!!.stock,
                )
            }
        }
    }

}
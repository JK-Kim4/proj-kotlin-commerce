package com.tutomato.commerce.interfaces.product

import com.tutomato.commerce.domain.product.ProductResult
import java.math.BigDecimal

class ProductResponse {

    class Product (
        val id: Long,
        val name: String,
        val description: String,
        val price : BigDecimal,
        val options: List<OptionResponse>,
    ) {
        companion object {
            fun from(product: ProductResult.Product): ProductResponse.Product {
                return ProductResponse.Product(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    options = product.options.map { OptionResponse.from(it) },
                )
            }
        }
    }

    class Products(
        val products: List<ProductResponse.Product>
    ) {
        companion object {
            fun from(products: ProductResult.Products): Products {
                return Products(
                    products.products.map { ProductResponse.Product.from(it) }
                )
            }
        }
    }

    class OptionResponse (
        val id: Long,
        val colorCode: String,
        val size: String,
        val stock: Int,
    ) {
        companion object {
            fun from(option: ProductResult.OptionResult): OptionResponse {
                return OptionResponse(
                    id = option.id,
                    colorCode = option.colorCode,
                    size = option.size,
                    stock = option.stock,
                )
            }
        }
    }

    class Popular (val id: Long, val name: String, val popularityRank: Int, val salesCount: Int)

    class Populars (val products: List<ProductResponse.Popular>)

}
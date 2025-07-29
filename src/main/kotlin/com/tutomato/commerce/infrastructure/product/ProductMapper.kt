package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Product

class ProductMapper {

    companion object {

        fun optionsMappingToProduct(options : List<Option>, product: List<Product>): List<Product> {
            return product.map { prod ->
                prod.apply {
                    addOptions(options.filter { it.product?.equals(product) ?: false })
                }
            }.toList()
        }

    }

}
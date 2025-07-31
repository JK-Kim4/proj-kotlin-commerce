package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Product

class ProductMapper {

    companion object {
        fun optionsMappingToProduct(products: List<Product>, options : List<Option>): List<Product> {
            return products.map { prod ->
                prod.apply {
                    addOptions(options.filter { it.product == prod })
                }
            }.toList()
        }
    }

}
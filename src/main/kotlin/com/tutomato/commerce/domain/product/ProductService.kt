package com.tutomato.commerce.domain.product

import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun save(command : ProductSave) {
        productRepository.save(command.toEntity())
    }

    fun findById(id : Long) : ProductResult.Product =
        productRepository.findById(id)
            .let { ProductResult.Product.from(it) }


    fun findAll(): ProductResult.Products =
        productRepository.findAll()
            .let { ProductResult.Products.from(it) }

    fun decreaseStock(command : DecreaseStock) {
        // 옵션 조회
        productRepository.findById(command.productId)
            .let { product ->
                product.optionById(command.optionId) }
            .let { option ->
                option.decreaseStock(command.decreaseAmount)
                productRepository.save(option)
            }
    }


}




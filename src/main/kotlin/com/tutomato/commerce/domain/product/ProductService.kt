package com.tutomato.commerce.domain.product

import jakarta.persistence.NoResultException
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun save(command : ProductCommand.Save) {
        productRepository.save(command.toEntity())
    }

    fun findById(id : Long) : ProductResult.Product {
        val product = productRepository.findById(id)
            .orElseThrow {  NoResultException("조회 결과가 존재하지않습니다.") }
        return ProductResult.Product.from(product)
    }

    fun findAll(): ProductResult.Products {
        val products = productRepository.findAll()

        return ProductResult.Products.from(products)
    }

    fun decreaseStock(command : ProductCommand.DecreaseStock) {
        // 옵션 조회
        var product = productRepository.findById(command.productId)
            .orElseThrow()
        var option = product.optionById(command.optionId)

        // 재고 차감
        option.decreaseStock(command.decreaseAmount)

        //영속화
        productRepository.save(option)

    }


}




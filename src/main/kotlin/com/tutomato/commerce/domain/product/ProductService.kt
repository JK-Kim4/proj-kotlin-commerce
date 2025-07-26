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


}




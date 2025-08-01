package com.tutomato.commerce.domain.product

import jakarta.persistence.NoResultException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductService(private val productRepository: ProductRepository) {

    fun save(command: ProductSave): Product {
        return productRepository.save(command.toEntity())
    }

    fun save(command: ProductOptionSave): Option {
        val product = productRepository.findById(command.productId)
            ?: throw NoResultException("상품이 존재하지않습니다.")

        return productRepository.save(command.toEntity(product))
    }

    fun decreaseStock(command : DecreaseStock) {
        val option = productRepository.findOptionById(command.optionId)
            ?: throw NoResultException("상품 옵션이 존재하지않습니다.")

        option.decreaseStock(command.decreaseAmount)

        productRepository.save(option)
    }

    fun updateStatus(command : UpdateStatus) {
        val product = productRepository.findById(command.productId)
            ?: throw NoResultException("상품이 존재하지않습니다.")

        product.updateStatus(SaleStatus.valueOf(command.updateStatus))

        productRepository.save(product)
    }

    @Transactional(readOnly = true)
    fun findById(id : Long) : ProductResult.Product {
        val product = productRepository.findById(id)
            ?: throw NoResultException("상품이 존재하지않습니다.")

        return ProductResult.Product.from(product)
    }


    @Transactional(readOnly = true)
    fun findAll(): ProductResult.Products {
        return productRepository.findAll()
            .let { ProductResult.Products.from(it) }
    }

}




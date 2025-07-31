package com.tutomato.commerce.domain.product

import jakarta.persistence.NoResultException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

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

        when (SaleStatus.valueOf(command.updateStatus)) {
            SaleStatus.SALE_STOPPED -> product.stopSales(LocalDateTime.now())
            SaleStatus.SOLD_OUT -> product.soldOut(LocalDateTime.now())
            SaleStatus.ON_SALE -> product.resumeSales(LocalDateTime.now())
        }

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




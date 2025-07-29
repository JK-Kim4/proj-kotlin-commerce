package com.tutomato.commerce.domain.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ProductService(private val productRepository: ProductRepository) {

    fun save(command : ProductSave) {
        productRepository.save(command.toEntity())
    }

    fun decreaseStock(command : DecreaseStock) {
        // 옵션 조회
        productRepository.findById(command.productId)
            .optionById(command.optionId)
            .let { option ->
                option.decreaseStock(command.decreaseAmount)
                productRepository.save(option)
            }
    }

    fun updateStatus(command : UpdateStatus) {
        val product = productRepository.findById(command.productId)

        when (SaleStatus.valueOf(command.updateStatus)) {
            SaleStatus.SALE_STOPPED -> product.stopSales(LocalDateTime.now())
            SaleStatus.SOLD_OUT -> product.soldOut(LocalDateTime.now())
            SaleStatus.ON_SALE -> product.resumeSales(LocalDateTime.now())
        }

        productRepository.save(product)
    }

    @Transactional(readOnly = true)
    fun findById(id : Long) : ProductResult =
        productRepository.findById(id)
            .let { ProductResult.from(it) }

    @Transactional(readOnly = true)
    fun findAll(): ProductResults =
        productRepository.findAll()
            .let { ProductResults.from(it) }
}




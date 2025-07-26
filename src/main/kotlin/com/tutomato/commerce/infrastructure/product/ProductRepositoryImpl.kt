package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Product
import com.tutomato.commerce.domain.product.ProductRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val optionJpaRepository: OptionJpaRepository
) : ProductRepository {

    override fun save(product: Product) {
        productJpaRepository.save(product)

        var options = product.availableOptions

        options!!.setProductId(product.id!!)

        optionJpaRepository.saveAll(options.options)
    }

    override fun save(option: Option) {
        optionJpaRepository.save<Option>(option)
    }

    override fun findAll(): List<Product> {
        var products = productJpaRepository.findAll()

        var productIds = products.map { it.id!! }.toSet()

        var options = optionJpaRepository.findByProductIdIn(productIds)

        return ProductMapper.optionsMappingToProduct(options, products)
    }

    override fun findById(productId: Long): Optional<Product> {
        return productJpaRepository.findById(productId);
    }
}
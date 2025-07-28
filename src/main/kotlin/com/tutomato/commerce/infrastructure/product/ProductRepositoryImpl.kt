package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Product
import com.tutomato.commerce.domain.product.ProductRepository
import jakarta.persistence.NoResultException
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val optionJpaRepository: OptionJpaRepository
) : ProductRepository {

    override fun save(product: Product) {
        productJpaRepository.save(product)

        var options = product.availableOptions

        options.setProductId(product.id)

        optionJpaRepository.saveAll(options.options)
    }

    override fun save(option: Option) {
        optionJpaRepository.save<Option>(option)
    }

    override fun findAll(): List<Product> {
        var products = productJpaRepository.findAll()

        var productIds = products.map { it.id }.toSet()

        var options = optionJpaRepository.findByProductIdIn(productIds)

        return ProductMapper.optionsMappingToProduct(options, products)
    }

    override fun findById(productId: Long): Product {
        return productJpaRepository.findById(productId)
            .orElseThrow { NoResultException("조회 결과가 존재하지않습니다.") }
            .apply { addOptions(optionJpaRepository.findByProductId(productId)) }
    }

    override fun findOptionById(optionId: Long): Option {
        return optionJpaRepository.findById(optionId)
            .orElseThrow { NoResultException("조회 결과가 존재하지않습니다.") }
    }
}
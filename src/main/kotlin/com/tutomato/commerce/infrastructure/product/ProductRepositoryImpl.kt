package com.tutomato.commerce.infrastructure.product

import com.tutomato.commerce.domain.product.Option
import com.tutomato.commerce.domain.product.Product
import com.tutomato.commerce.domain.product.ProductRepository
import jakarta.persistence.NoResultException
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val optionJpaRepository: OptionJpaRepository,
) : ProductRepository {

    override fun save(product: Product): Product{
        return productJpaRepository.save(product)
            .also {
                val options = product.availableOptions
                options.setProduct(it)
                optionJpaRepository.saveAll(options.options)
            }
    }

    override fun saveAll(products: List<Product>) {
        products.forEach { product -> save(product) }
    }

    override fun flush() {
        productJpaRepository.flush()
    }

    override fun save(option: Option): Option {
        return optionJpaRepository.save<Option>(option)
    }

    override fun findAll(): List<Product> {
        return productJpaRepository.findAll()
            .let { products ->
                val productIds = products.map { it.id }.toSet()
                val options = optionJpaRepository.findByProductIdIn(productIds)
                ProductMapper.optionsMappingToProduct(products, options)
            }
    }

    override fun findById(productId: Long): Product {
        return productJpaRepository.findById(productId)
            .orElseThrow { NoResultException("조회 결과가 존재하지않습니다.") }
            .apply { initializeOptions(optionJpaRepository.findByProductId(productId)) }
    }

    override fun findOptionById(optionId: Long): Option? {
        return optionJpaRepository.findById(optionId).orElse(null)
    }

    override fun findByIds(productIds: Set<Long>): List<Product> {
        return productJpaRepository.findByIdsIn(productIds)
    }

    override fun findOptionByOptionIdWithPessimisticLock(optionId: Long): Option? {
        return optionJpaRepository.findOptionByOptionIdWithPessimisticLock(optionId)
    }

    override fun findAllOptionsByIdInForUpdate(optionIds: Set<Long>): List<Option> {
        return optionJpaRepository.findAllOptionsByIdInForUpdate(optionIds)
    }
}
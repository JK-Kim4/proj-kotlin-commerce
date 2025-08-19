package com.tutomato.commerce.config.initializer

import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.product.*
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
class ProductDataInitializer {

    val env = org.springframework.core.env.StandardEnvironment()
    val availableColors = listOf("RED", "BLUE", "GREEN", "YELLOW", "BLACK", "WHITE")
    val sizes = Size.values().toList()

    @Bean
    fun insertDummyProducts(productRepository: ProductRepository): CommandLineRunner {

        return CommandLineRunner {
            if (!env.activeProfiles.contains("local")) return@CommandLineRunner

            val batchSize = 1000
            val totalCount = 100_000
            val products = mutableListOf<Product>()

            for (i in 1..totalCount) {
                val product = Product(
                    info = ProductInfo(
                        name = "Test Product $i",
                        description = "상품 설명 $i",
                        publishedDate = LocalDate.of(2024,12, 31)
                    ),
                    saleStatus = SaleStatus.ON_SALE,
                    category = Category.TOP,
                    price = Money(BigDecimal.valueOf(10000)),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )

                product.availableOptions =
                    Options(
                        generateUniqueRandomOptions(
                            product = product,
                            availableColors = availableColors,
                            sizes = sizes,
                        )
                    )

                products.add(product)

                if (i % batchSize == 0) {
                    productRepository.saveAll(products)
                    productRepository.flush()
                    products.clear()
                    println(">> Inserted $i products")
                }
            }

            // 남은 것 처리
            if (products.isNotEmpty()) {
                productRepository.saveAll(products)
                productRepository.flush()
                println(">> Inserted remaining ${products.size} products")
            }

            println("✅ Product insert completed.")
        }
    }

    fun generateUniqueRandomOptions(
        product: Product,
        availableColors: List<String>,
        sizes: List<Size>,
        optionCount: Int = (2..5).random()
    ): List<Option> {
        val usedCombinations = mutableSetOf<Pair<String, Size>>()
        val options = mutableListOf<Option>()

        while (options.size < optionCount) {
            val colorCode = availableColors.random()
            val size = sizes.random()

            val key = Pair(colorCode, size)
            if (usedCombinations.contains(key)) continue

            usedCombinations.add(key)

            options.add(
                Option(
                    product = product,
                    color = Color(colorCode),
                    size = size,
                    stock = Stock((10..100).random())
                )
            )
        }

        return options
    }
}
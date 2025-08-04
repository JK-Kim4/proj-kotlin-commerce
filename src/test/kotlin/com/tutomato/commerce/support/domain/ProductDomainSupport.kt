package com.tutomato.commerce.support.domain

import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.product.*
import java.math.BigDecimal
import java.time.LocalDate

object ProductDomainSupport {

    fun fixture(
        id: Long = 0L,
        name: String = "테스트 상품",
        description: String = "상품 설명",
        publishedDate: LocalDate = LocalDate.now(),
        saleStatus: SaleStatus? = null,
        category: Category = Category.TOP,
        options: Options = Options(),
        price: BigDecimal = BigDecimal.valueOf(5000),
    ): Product {
        val product = Product(
            id = id,
            info = ProductInfo(name, description, publishedDate),
            saleStatus = saleStatus,
            category = category,
            price = Money(price)
        )

        if (options.options.size > 0) {
            options.setProduct(product)
            product.availableOptions = options
        }

        return product
    }
}
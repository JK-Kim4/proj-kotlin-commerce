package com.tutomato.commerce.support.domain

import com.tutomato.commerce.common.model.Money
import com.tutomato.commerce.domain.product.*
import java.math.BigDecimal
import java.time.LocalDate

class ProductDomainSupport {

    companion object {

        private val defaultInfo = ProductInfo(
            "기본 상품",
            "기본 상품 정보",
            LocalDate.of(2025, 1, 1)
        )

        fun 기본_상품_생성() : Product {
            return Product(
                info = defaultInfo,
                saleStatus = SaleStatus.ON_SALE,
                category = Category.TOP,
                price = Money(BigDecimal.valueOf(5000))
            )
        }

        fun 상품_옵션_전달받아_상품_생성(option: Option) : Product{
            var product = Product(
                info = defaultInfo,
                saleStatus = SaleStatus.ON_SALE,
                category = Category.TOP,
                price = Money(BigDecimal.valueOf(5000)),
            )

            product.addOption(option)

            return product
        }

        fun 상품_옵션_목록을_전달받아_상품_생성(option: List<Option>) : Product{
            var product = Product(
                info = defaultInfo,
                saleStatus = SaleStatus.ON_SALE,
                category = Category.TOP,
                price = Money(BigDecimal.valueOf(5000)),
            )

            option.forEach { product.addOption(it) }

            return product
        }

        fun 고유번호를_보유한_상품객체_생성(id : Long) : Product{
            var product = Product(
                id = id,
                info = defaultInfo,
                saleStatus = SaleStatus.ON_SALE,
                category = Category.TOP,
                price = Money(BigDecimal.valueOf(5000)),
            )

            return product
        }

    }
}
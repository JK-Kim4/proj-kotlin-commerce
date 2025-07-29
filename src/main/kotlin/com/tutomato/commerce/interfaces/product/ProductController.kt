package com.tutomato.commerce.interfaces.product

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/products")
class ProductController: ProductApiSpec {

    //val id: Long, val name: String, val description: String, val price : BigDecimal, val stock: Int
    @GetMapping
    override fun products(): ResponseEntity<ProductResponse.Products> {
        return ResponseEntity.ok(ProductResponse.Products(listOf(
            ProductResponse.Product(1L, "테스트 상품1", "테스트용 상품1입니다.", BigDecimal(5000), 10),
            ProductResponse.Product(2L, "테스트 상품2", "테스트용 상품2입니다.", BigDecimal(3000), 10),
            ProductResponse.Product(3L, "테스트 상품3", "테스트용 상품3입니다.", BigDecimal(50000), 10)
        )))
    }

    @GetMapping("/{id}")
    override fun product(@PathVariable id: Long
    ): ResponseEntity<ProductResponse.Product> {
        return ResponseEntity.ok(
            ProductResponse.Product(id, "테스트 상품3", "테스트용 상품3입니다.", BigDecimal(50000), 10))
    }

    @GetMapping("/popular")
    override fun popular(): ResponseEntity<ProductResponse.Populars> {
        return ResponseEntity.ok(ProductResponse.Populars(listOf(
            ProductResponse.Popular(1L, "인기 판매 상품1", 1, 50),
            ProductResponse.Popular(2L, "인기 판매 상품2", 2, 30),
            ProductResponse.Popular(3L, "인기 판매 상품3", 3, 20),
        )))
    }

}
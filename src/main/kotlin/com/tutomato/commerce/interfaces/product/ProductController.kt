package com.tutomato.commerce.interfaces.product

import com.tutomato.commerce.domain.product.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService,
): ProductApiSpec {

    @GetMapping
    override fun products(
    ): ResponseEntity<ProductResponse.Products> {
        return ResponseEntity.ok(
            ProductResponse.Products.from(productService.findAll())
        )
    }

    @GetMapping("/{id}")
    override fun product(
        @PathVariable id: Long
    ): ResponseEntity<ProductResponse.Product> {
        return ResponseEntity.ok(
            ProductResponse.Product.from(productService.findById(id))
        )
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
package com.tutomato.commerce.interfaces.product

import org.springframework.http.ResponseEntity

interface ProductApiSpec {

    fun products(): ResponseEntity<ProductResponse.Products>

    fun product(id: Long): ResponseEntity<ProductResponse.Product>

    fun popular(): ResponseEntity<ProductResponse.Populars>

}
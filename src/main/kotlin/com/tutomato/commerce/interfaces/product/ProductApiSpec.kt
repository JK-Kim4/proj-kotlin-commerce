package com.tutomato.commerce.interfaces.product

import org.springframework.http.ResponseEntity

interface ProductApiSpec {

    fun products(): ResponseEntity<ProductResponses>

    fun product(id: Long): ResponseEntity<ProductResponse>

    fun popular(): ResponseEntity<PopularProductResponses>

}
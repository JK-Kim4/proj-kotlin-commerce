package com.tutomato.commerce.domain.product

import java.time.LocalDate

class ProductInfo(

    val name: String,
    val description: String,
    val seller: Seller,
    val publishedDate: LocalDate,

    ) {

}

package com.tutomato.commerce.domain.product

import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
class ProductInfo(

    val name: String,
    val description: String,
    val publishedDate: LocalDate,
    //val seller: Seller,

    ) {

}

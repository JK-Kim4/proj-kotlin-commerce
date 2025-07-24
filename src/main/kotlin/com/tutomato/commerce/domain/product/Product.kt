package com.tutomato.commerce.domain.product

import java.time.LocalDateTime

class Product (

    val id: Long,
    val info: ProductInfo,
    val saleStatus: SaleStatus,
    val availableOption: Options,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,

    ){

    /*구매 가능 여부*/
    fun canOrder() : Boolean {
        return this.saleStatus == SaleStatus.ON_SALE;
    }

}
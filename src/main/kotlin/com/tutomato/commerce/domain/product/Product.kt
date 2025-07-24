package com.tutomato.commerce.domain.product

import java.time.LocalDateTime

class Product (

    val id: Long,
    var info: ProductInfo,
    var saleStatus: SaleStatus,
    val category: Category,
    var availableOptions: Options,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,

    ){

    fun canOrder() : Boolean {
        return this.saleStatus == SaleStatus.ON_SALE;
    }

    fun getStockByOption(option: Option): Stock? {
        return availableOptions.getStockByOption(option)
    }

    fun addOption(option: Option) {
        availableOptions = availableOptions.add(option)
    }

    fun removeOption(option: Option) {
        availableOptions = availableOptions.remove(option)
    }

    fun updateProductInfo(info: ProductInfo) {
        this.info = info;
    }

    fun stopSales(processDate: LocalDateTime) {
        this.saleStatus = SaleStatus.SALE_STOPPED
        this.updatedAt = processDate
    }

    fun soldOut(processDate: LocalDateTime) {
        this.saleStatus = SaleStatus.SOLD_OUT
        this.updatedAt = processDate
    }

    fun resumeSales(processDate: LocalDateTime) {
        this.saleStatus = SaleStatus.SALE_STOPPED
        this.updatedAt = processDate
    }

}
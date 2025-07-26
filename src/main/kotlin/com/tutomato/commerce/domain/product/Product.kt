package com.tutomato.commerce.domain.product

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Product (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L,

    @Embedded
    var info: ProductInfo,

    @Enumerated(EnumType.STRING)
    var saleStatus: SaleStatus? = SaleStatus.ON_SALE,

    @Enumerated(EnumType.STRING)
    val category: Category,

    @Transient
    var availableOptions: Options? = Options(),

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    ){

    fun canOrder() : Boolean {
        return this.saleStatus == SaleStatus.ON_SALE;
    }

    fun getStockByOption(option: Option): Stock? {
        return availableOptions?.getStockByOption(option)
    }

    fun addOption(option: Option) {
        availableOptions = availableOptions?.add(option)
    }

    fun addOptions(options: List<Option>) {
        availableOptions = Options(options)
    }

    fun getOptions() : Options? {
        return this.availableOptions
    }

    fun removeOption(option: Option) {
        availableOptions = availableOptions?.remove(option)
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
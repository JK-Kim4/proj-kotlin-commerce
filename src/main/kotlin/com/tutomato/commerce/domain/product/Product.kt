package com.tutomato.commerce.domain.product

import com.tutomato.commerce.common.model.Money
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
class Product (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Embedded
    var info: ProductInfo,

    @Enumerated(EnumType.STRING)
    var saleStatus: SaleStatus? = SaleStatus.ON_SALE,

    @Enumerated(EnumType.STRING)
    val category: Category,

    @Embedded
    val price: Money,

    @Transient
    var availableOptions: Options = Options(),

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @LastModifiedDate
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    ){

    fun canOrder(option: Option, quantity: Int): Boolean {
        return saleStatus == SaleStatus.ON_SALE &&
                availableOptions.findOption(option)?.hasEnoughStock(quantity) == true
    }

    fun initializeOptions(options: List<Option>) {
        availableOptions = Options(options)
    }

    fun alreadyExistOption(colorCode: String, size: String): Boolean {
        return availableOptions.findOptionByColorAndSize(colorCode, size) != null
    }

    fun updateStatus(status: SaleStatus, updatedAt: LocalDateTime = LocalDateTime.now()) {
        when (status) {
            SaleStatus.SALE_STOPPED -> stopSales(updatedAt)
            SaleStatus.SOLD_OUT -> soldOut(updatedAt)
            SaleStatus.ON_SALE -> resumeSales(updatedAt)
        }
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
        this.saleStatus = SaleStatus.ON_SALE
        this.updatedAt = processDate
    }

}
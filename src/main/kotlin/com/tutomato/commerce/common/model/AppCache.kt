package com.tutomato.commerce.common.model

import java.time.Duration

enum class AppCache(
    val base: String,
    val ttl: Duration,
    val maxIdle: Duration = ttl
) {

    PRODUCT_POPULAR(
        base = "product:popular",
        ttl = Duration.ofMinutes(60),
        maxIdle = Duration.ofMinutes(60),
    );

    companion object {
        const val PRODUCT_POPULAR_BASE = "product:popular"
        val ALL = entries.toList()
    }

}
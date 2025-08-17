package com.tutomato.commerce.support.async

import java.time.Duration

class AsyncSupport {


    companion object {
        fun <T> waitUntilNotNull(timeoutSeconds: Long, check: () -> T?): T? {
            val start = System.currentTimeMillis()
            val timeout = Duration.ofSeconds(timeoutSeconds).toMillis()
            while (System.currentTimeMillis() - start < timeout) {
                val result = check()
                if (result != null) return result
                Thread.sleep(100)
            }
            return null
        }
    }
}
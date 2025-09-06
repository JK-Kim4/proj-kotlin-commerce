package com.tutomato.commerce.config.redis

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    fun redissonClient(
        @Value("\${app.redisson.address}") address: String,
        @Value("\${app.redisson.database}") database: Int,
    ): RedissonClient {
        val config = Config()
        config.useSingleServer()
            .setAddress(address)
            .setDatabase(database)

        return Redisson.create(config)
    }
}
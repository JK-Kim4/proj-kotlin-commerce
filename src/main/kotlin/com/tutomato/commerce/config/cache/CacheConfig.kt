package com.tutomato.commerce.config.cache

import com.tutomato.commerce.common.model.AppCache
import org.redisson.api.RedissonClient
import org.redisson.spring.cache.CacheConfig
import org.redisson.spring.cache.RedissonSpringCacheManager
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class CacheConfig(
    private val redisson: RedissonClient
): CachingConfigurer {

    @Bean
    override fun cacheManager(): CacheManager {
        val redissonCacheConfig = AppCache.ALL.associate { spec ->
            spec.base to CacheConfig(spec.ttl.toMillis(), spec.maxIdle.toMillis())
        }

        return RedissonSpringCacheManager(redisson, redissonCacheConfig)
    }

    @Bean
    override fun keyGenerator(): KeyGenerator =
        KeyGenerator { _, method, params ->
            buildString {
                append(method.declaringClass.simpleName).append(":").append(method.name)
                params.forEach { append(":").append(it) }
            }
        }
}
package com.tinqinacademy.authentication.rest.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class CaffeineCacheConfig {

    private static int CACHE_ENTRY_DURATION;
    private final String JWT_BLACKLIST;

    public CaffeineCacheConfig(@Value("JWT_BLACKLIST") String JWT_BLACKLIST,
                               @Value("5000") int cacheEntryDuration) {
        this.JWT_BLACKLIST = JWT_BLACKLIST;
        CACHE_ENTRY_DURATION = cacheEntryDuration;
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache(JWT_BLACKLIST, jwtBlacklist());

        //! To avoid dynamic caches and be sure each name is assigned to a specific config (dynamic = false)
        //! throws error when tries to use a new cache
        cacheManager.setCacheNames(Collections.emptyList());

        return cacheManager;
    }

    private static Cache<Object,Object> jwtBlacklist() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(CACHE_ENTRY_DURATION))
                .build();
    }

}

package com.gonglian.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cn.hutool.cache.impl.TimedCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheUtil {

    /**
     * 创建 Guava 本地缓存
     */
    public static <K, V> Cache<K, V> buildLocalCache(long duration, TimeUnit timeUnit) {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(duration, timeUnit)
                .maximumSize(1000)
                .build();
    }

    /**
     * 使用 Hutool 的缓存工具
     */
    @SuppressWarnings("unchecked")
    public static <T> T getOrCreate(String key, java.util.function.Supplier<T> supplier) {
        // 创建一个超时时间为1小时的缓存
        TimedCache<String, Object> cache = cn.hutool.cache.CacheUtil.newTimedCache(3600 * 1000);
        Object value = cache.get(key);
        if (value == null) {
            value = supplier.get();
            cache.put(key, value);
        }
        return (T) value;
    }
} 
package com.gonglian.utils;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtil {

    private CollectionUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 使用 Guava 的不可变集合
     */
    public static <T> List<T> immutableList(Collection<T> list) {
        return ImmutableList.copyOf(list);
    }

    /**
     * 使用 Guava 的集合分片
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null) {
            return Lists.newArrayList();
        }
        return Lists.partition(list, Math.max(1, size));
    }

    /**
     * 结合 Stream 和 Guava 的过滤操作
     */
    public static <T> List<T> filterList(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null || predicate == null) {
            return Lists.newArrayList();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 使用 Guava 的 Sets 工具类
     */
    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            return Sets.newHashSet();
        }
        return Sets.union(set1, set2);
    }

    /**
     * 使用 Hutool 的集合工具类判断是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollUtil.isEmpty(collection);
    }

    /**
     * 使用 Hutool 的集合工具类创建ArrayList
     */
    @SafeVarargs
    public static <T> List<T> newArrayList(T... values) {
        return CollUtil.newArrayList(values);
    }
} 
package com.gonglian.utils;

import cn.hutool.core.lang.func.Func1;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FunctionalUtil {

    private FunctionalUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * List转换工具，结合 Guava 和 Stream
     */
    public static <F, T> List<T> transformList(Collection<F> fromList, Function<? super F, ? extends T> function) {
        if (fromList == null || function == null) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(Lists.transform(Lists.newArrayList(fromList), function));
    }

    /**
     * 使用 Hutool 的函数式接口进行List转换
     */
    public static <F, T> List<T> convertList(Collection<F> fromList, Func1<F, T> mapper) {
        if (fromList == null || mapper == null) {
            return Lists.newArrayList();
        }
        try {
            return fromList.stream()
                    .map(item -> {
                        try {
                            return mapper.call(item);
                        } catch (Exception e) {
                            log.error("Error converting item: {}", item, e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error converting list", e);
            return Lists.newArrayList();
        }
    }

    /**
     * 使用 Guava 的 Maps 工具类构建 Map
     */
    public static <K, V> Map<K, V> listToMap(Collection<V> list, Function<V, K> keyFunction) {
        if (list == null || keyFunction == null) {
            return Maps.newHashMap();
        }
        try {
            return Maps.uniqueIndex(list, keyFunction::apply);
        } catch (Exception e) {
            log.error("Error converting list to map", e);
            return Maps.newHashMap();
        }
    }

    /**
     * 使用 Guava 的 Sets 工具类进行集合操作
     */
    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            return Sets.newHashSet();
        }
        return Sets.intersection(set1, set2);
    }
} 
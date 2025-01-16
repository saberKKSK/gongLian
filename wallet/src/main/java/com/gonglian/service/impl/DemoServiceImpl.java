package com.gonglian.service.impl;

import com.gonglian.utils.CollectionUtil;
import com.gonglian.utils.FunctionalUtil;
import com.gonglian.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DemoServiceImpl {

    public void functionalExample() {
        // 列表转换示例
        List<String> numbers = Arrays.asList("1", "2", "3");
        List<Integer> integers = FunctionalUtil.convertList(numbers, Integer::valueOf);
        
        // 列表转Map示例
        Map<Integer, String> numberMap = FunctionalUtil.listToMap(numbers, Integer::valueOf);
        
        // 字符串处理示例
        String joined = StringUtil.join(numbers, ",");
        List<String> split = StringUtil.split("a,b,c", ",");
        
        // 集合操作示例
        List<List<String>> partitions = CollectionUtil.partition(numbers, 2);
        
        // 函数式过滤示例
        List<String> filtered = CollectionUtil.filterList(numbers, 
            str -> Integer.parseInt(str) > 1);
    }
} 
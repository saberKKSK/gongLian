package com.gonglian.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.List;

public class StringUtil {

    /**
     * 使用 Guava 的 Joiner
     */
    public static String join(List<String> list, String separator) {
        return Joiner.on(separator)
                .skipNulls()
                .join(list);
    }

    /**
     * 使用 Guava 的 Splitter
     */
    public static List<String> split(String str, String separator) {
        return Splitter.on(separator)
                .trimResults()
                .omitEmptyStrings()
                .splitToList(str);
    }

    /**
     * 使用 Hutool 的字符串工具
     */
    public static String format(String template, Object... params) {
        return StrUtil.format(template, params);
    }

    /**
     * 使用 Guava 的命名格式转换
     */
    public static String camelToUnderScore(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }
} 
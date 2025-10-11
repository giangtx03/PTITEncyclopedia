package com.project.ptittoanthu.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RedisUtils {

    public String createKey(String... parts) {
        if (parts == null || parts.length == 0) {
            throw new IllegalArgumentException("Key parts cannot be null or empty");
        }
        return String.join(":", parts);
    }
}

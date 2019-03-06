package com.aooled_laptop.aooled.util;

public class StringUtils {
    public static boolean isEmpty(String str){
        if (str == null || "".equals(str))
            return false;
        return true;
    }
}

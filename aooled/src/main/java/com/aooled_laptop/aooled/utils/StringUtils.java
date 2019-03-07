package com.aooled_laptop.aooled.utils;

public class StringUtils {
    public static boolean isEmpty(String str){
        if (str == null || "".equals(str))
            return true;
        return false;
    }
}

package com.aooled_laptop.aooled.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {

    public static String getTimestamp(String timeString){
        String timestamp = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date d;
        try{
            d = simpleDateFormat.parse(timeString);
            long l = d.getTime();
            timestamp = String.valueOf(l);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return timestamp;
    }

    public static String getCurrentTime(String timestamp){
        String currentTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        long l = Long.valueOf(timestamp + "000");
        currentTime = simpleDateFormat.format(new Date(l));
        return currentTime;
    }
}

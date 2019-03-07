package com.aooled_laptop.aooled.utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class SaveInfoUtils {
    public static void saveLoginInfo(Context context, HashMap<String, String> hashMap){
        File file = new File(context.getFilesDir() + "/login.properties");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter pw = new PrintWriter(file);
            Properties properties = new Properties();
            properties.setProperty("username", hashMap.get("username"));
            properties.setProperty("password", hashMap.get("password"));
            properties.list(pw);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getLoginInfo(Context context){
        File file = new File(context.getFilesDir() + "/login.properties");
        if(!file.exists()) {
            return null;
        }
        try {
            FileReader fr = new FileReader(file);
            Properties properties = new Properties();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            properties.load(fr);
            hashMap.put("username", properties.getProperty("username"));
            hashMap.put("password", properties.getProperty("password"));
            return hashMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delLoginInfo(Context context){
        File file = new File(context.getFilesDir() + "/login.properties");
        if (file.exists())
            file.delete();
    }

}

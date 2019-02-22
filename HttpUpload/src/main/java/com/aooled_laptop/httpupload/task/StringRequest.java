package com.aooled_laptop.httpupload.task;

import android.util.Log;

import com.aooled_laptop.httpupload.util.Logger;

public class StringRequest extends Request<String> {


    public StringRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public StringRequest(String url, RequestMethod method) {
        super(url, method);
//        setHeader("Accept", "application/json");
//        setHeader("Accept", "application/xml");
        setHeader("Accept", "*");


    }

    public String parseResponse(byte[] responseBody)  throws Exception {
        return parseResponseString(responseBody);
    }
//    @Override
    public static String parseResponseString(byte[] responseBody) {
//        Logger.i(new String(responseBody));
        if (responseBody != null && responseBody.length > 0)
            return new String(responseBody);
        return "";
    }
}

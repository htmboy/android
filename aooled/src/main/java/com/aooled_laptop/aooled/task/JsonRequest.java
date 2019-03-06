package com.aooled_laptop.aooled.task;

import android.util.Log;

import org.json.JSONObject;

public class JsonRequest extends Request<JSONObject> {
    public JsonRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public JsonRequest(String url, RequestMethod method) {

        super(url, method);
        setHeader("Accept", "application/json");
    }

    @Override
    public JSONObject parseResponse(byte[] responseBody) throws Exception{
        String result = StringRequest.parseResponseString(responseBody);
        if (result.length() >= 2){
            Log.i("kkk", new String(responseBody));
            // json 处理
            return null; // 返回JSONObject
        }
        return null;// 返回空的一个JSONObject
    }
}

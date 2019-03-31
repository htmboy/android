package com.aooled_laptop.aooled.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Constants {

    // 本地接口
    public static final String URL_SUBMIT = "http://139.199.77.144/api/main";
    public static final String URL_UPLOAD = "http://139.199.77.144/api/upload";
    public static final String URL_IMAGE = "http://139.199.77.144";
    public static JSONObject jsonObject;
    public static final String ORDER_STATUS = "{" +
            "\"11\":已录入订单信息, \"12\":已录入快递信息, \"13\":已录入收款信息, \"14\":上传生产单, \"16\":录入信息有误," +
            "\"21\":第一审核, \"22\":第二审核, \"23\":放行条一审, \"24\":放行条二审, \"25\":生产单一审, \"26\":生产单二审," +
            "\"31\":生产单信息有误, \"32\":生产单修改申请, \"33\":生产单修改, \"34\":上传新的生产单," +
            "\"41\":上传放行条, \"42\":放行条有误, \"51\":放行条信息有误," +
            "\"61\":等待发货及收款, \"62\":收款中, \"63\":发货中, \"64\":等待发放提成, \"71\":已发放提成, \"81\":修改订单记录}";

    public static String getOrderStatus(String name){
        try {
            if (jsonObject == null)
                jsonObject = new JSONObject(ORDER_STATUS);
            return jsonObject.optString(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return "未获取到状态";
        }
    }
}

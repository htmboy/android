package com.aooled_laptop.aooled;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.model.Contact;
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.utils.Constants;
import com.aooled_laptop.aooled.utils.Logger;
import com.aooled_laptop.aooled.utils.MD5Util;
import com.aooled_laptop.aooled.utils.SaveInfoUtils;
import com.aooled_laptop.aooled.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameText;
    private EditText passwordText;
    private String username;
    private String password;
    private HashMap<String, String> hashMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);
        if ((hashMap = SaveInfoUtils.getLoginInfo(LoginActivity.this)) != null){
            usernameText.setText(hashMap.get("username"));
            passwordText.setText(hashMap.get("password"));
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                // 不相等 请求授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 1);
//            }
        }

    }

    @Override
    public void onClick(View v) {
        username = usernameText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        login();
    }

    private void login(){

        Request<String> request = new StringRequest(Constants.URL_SUBMIT, RequestMethod.JSON);
        request.add("username", username);
        request.add("password", MD5Util.getMD5(password));
        request.add("code", 1);
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {

            @Override
            public void onSucceed(Response<String> response) {
                JSONObject jsonObject = null;
                String str = response.get();
                Logger.i(str);
                int code = 0;
                try {
                    jsonObject = new JSONObject(str);
                    code = (int) jsonObject.opt("code");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (code){
                    case 0: Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("username", username);
                        hashMap.put("password", password);
                        SaveInfoUtils.saveLoginInfo(getApplicationContext(), hashMap);
                        JSONObject userInfo = jsonObject.optJSONObject("userInfo");
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", userInfo.optString("id"));
                        bundle.putString("username", userInfo.optString("username"));
                        bundle.putString("name", userInfo.optString("name"));
                        bundle.putString("code", userInfo.optString("code"));
                        bundle.putString("sex", userInfo.optString("sex"));
                        bundle.putString("contacts", jsonObject.optString("contacts"));
                        bundle.putString("orders", jsonObject.optString("orders"));
                        bundle.putInt("totalItem", jsonObject.optInt("totalItem"));
                        intent.putExtras(bundle);
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Logger.i("login");

                        finish();
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailed(Exception e) {
                if (e instanceof ParseError){
                    // 数据解析异常
                    Logger.d("数据解析异常");
                }else if (e instanceof TimeoutError){
                    // 超时
                    Logger.d("超时");
                }else if (e instanceof UnknowHostError){
                    // 没有找到服务器
                    Logger.d("没有找到服务器");
                }else if (e instanceof URLError){
                    // URL格式错误
                    Logger.d("URL格式错误");
                }

            }
        });
    }
}

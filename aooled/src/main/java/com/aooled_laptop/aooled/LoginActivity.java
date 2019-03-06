package com.aooled_laptop.aooled;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.JsonRequest;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.util.Constants;
import com.aooled_laptop.aooled.util.Logger;
import com.aooled_laptop.aooled.util.StringUtils;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("username", username);
        request.add("password", password);
        request.add("code", 1);
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {

            @Override
            public void onSucceed(Response<String> response) {
                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());

                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
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
//        startActivity(new Intent(this, MainActivity.class));
//        finish();

    }
}

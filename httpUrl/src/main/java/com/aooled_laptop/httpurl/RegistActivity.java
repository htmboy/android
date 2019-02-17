package com.aooled_laptop.httpurl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistActivity extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private Button regist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        regist = findViewById(R.id.regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://139.199.77.144:8080";

                new HttpThreadRegist(url, name.getText().toString(), age.getText().toString()).start();
            }
        });

    }
}

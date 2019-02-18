package com.aooled_laptop.httpurl;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class UploadActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                File file = Environment.getExternalStorageDirectory();
                File fileAbs = new File(file, "Sky.jpg");
                String fileName = fileAbs.getAbsolutePath();
                UploadThread thread = new UploadThread(url, fileName);
                thread.start();
            }
        });
    }
}

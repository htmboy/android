package com.aooled_laptop.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class FourActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView1);
        // 接收intent来的数据
        Intent intent = getIntent();
        if (intent != null){
            // 接收大数据
//            int[] data = intent.getIntArrayExtra("data");
//            Log.i("data", "data = " + data);
            Bitmap bitmap = intent.getParcelableExtra("Bitmap");
            imageView.setImageBitmap(bitmap);

            // 接收图片
//            Bitmap bitmap = intent.getParcelableExtra("Bitmap");
//            imageView.setImageBitmap(bitmap);

            // 使用 javabean
//            Person person = (Person) intent.getSerializableExtra("person");
//            textView.setText(person.toString());

            //直接bundle
//            String name = intent.getStringExtra("name");
//            int age = intent.getIntExtra("age", 0);
//            textView.setText("name= " + name + " age=" + age);
        }
    }
}

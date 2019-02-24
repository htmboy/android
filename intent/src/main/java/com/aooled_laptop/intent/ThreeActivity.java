package com.aooled_laptop.intent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three);
        button = findViewById(R.id.button1);
        button.setOnClickListener(this);
    }

    // 传递大数据
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, FourActivity.class);
        Bundle bundle = new Bundle();

        int[] data = new int[1024 * 1024];
        // bundle.putIntArray("name", data); // 只能传0.5m bitmap 也是
        Bitmap bitmap = Bitmap.createBitmap(480, 1200, Bitmap.Config.ARGB_8888);
        bundle.putParcelable("Bitmap", bitmap);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    // 数据量大的时候可以使用java bean来传递数据 bitmap 应该尽可能的小
//    @Override
//    public void onClick(View v) {
//        // activity 之间使用 intent 加bundle来传递数据
//        // 把数据传到fouractivity
//        Intent intent = new Intent(this, FourActivity.class);
//        Person person = new Person(1, "htmboy", "foshan");
//        Bundle bundle = new Bundle();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        bundle.putParcelable("Bitmap", bitmap);
//        intent.putExtras(bundle);
//
//        startActivity(intent);
//    }

    // 数据量大的时候可以使用java bean来传递数据
//    @Override
//    public void onClick(View v) {
//        // activity 之间使用 intent 加bundle来传递数据
//        // 把数据传到fouractivity
//        Intent intent = new Intent(this, FourActivity.class);
//        Person person = new Person(1, "htmboy", "foshan");
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("person", person);
//        intent.putExtras(bundle);
//
//        startActivity(intent);
//    }

// activity 之间使用 intent 加bundle来传递数据
//    public void onClick(View v) {
//
//        Intent intent = new Intent(this, FourActivity.class);
//
//
//        Bundle bundle = new Bundle();
//        bundle.putString("name", "htmboy");
//        bundle.putInt("age", 23);
//        intent.putExtras(bundle);
//
//        startActivity(intent);
//    }

//    启动浏览器
//    public void onClick(View v) {
//
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri url = uri.parse("http://m.aooled.com");
//        intent.setData(url);
//
//        startActivity(intent);
//    }
}

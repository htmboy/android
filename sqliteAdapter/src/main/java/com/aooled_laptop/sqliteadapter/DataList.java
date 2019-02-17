package com.aooled_laptop.sqliteadapter;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.aooled_laptop.utils.Constant;

import java.io.File;

public class DataList extends AppCompatActivity {
    private ListView lv;
    private SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_layout);
        lv = findViewById(R.id.lv);

        // 1. 获取数据库查询的数据源
        // 得到sd卡的目录
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.db";
        /**
         * SQLiteDatabase.openDatabase() 打开数据库
         * 有三个参数
         * String Path 数据库的存放路劲
         * CursorFactory 为空就可以了
         * int flags 表示打开数据库的操作模式
         */
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from " + Constant.TABLE_NAME, null);

        // 2, 将数据源数据加载到适配器中
        /**
         * SimpleCursorAdapter(Context context, int list_layout, Cursor c, String[] from, int[] to, int flags)
         * Context context 上下文对象 指this
         * int list_layout 表示适配器控件中每项item的布局id
         * Cursor c
         * String[] from 表示cursor中数据表字段的数组
         * int[] to 表示展示字段对应值的控件资源id
         * int flags 设置适配器的标记
         */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_layout, cursor, new String[]{Constant._ID, Constant.NAME, Constant.AGE}, new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//
//         // 3, 将适配器的数据加载到控件
        lv.setAdapter(adapter);
    }
}


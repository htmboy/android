package com.aooled_laptop.sqliteadapter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.aooled_laptop.bean.Person;
import com.aooled_laptop.utils.Constant;
import com.aooled_laptop.utils.DbManger;

import java.io.File;
import java.util.List;

/**
 * 演示数据库分页
 * select * from person limit 0, 15;
 * 获取总条目 201
 * 定义页条数 20
 * 计算总页数
 * 计算页码
 *
 * 1, 页码为1时在listview中展示对应数据
 * 2, 当listview加载完本页数据分页加载下一页数据
 */
public class Pagination extends AppCompatActivity {
    private ListView lv;
    private SQLiteDatabase db;
    private int totalNum;
    private  int pageSize = 15;
    private int pageNum;
    private int currentpPage = 1;
    private List<Person> totalList; // 表示数据源
    private MyBaseAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_layout);

        lv = findViewById(R.id.lv);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        totalNum = DbManger.getDataCount(db, Constant.TABLE_NAME);
        pageNum = (int) Math.ceil(totalNum / (double)pageSize);
        if (currentpPage == 1)
            totalList = DbManger.getListByCurrentPage(db, Constant.TABLE_NAME, currentpPage, pageSize);
        adapter = new MyBaseAdapter(this, totalList);

        lv.setAdapter(adapter);
    }
}

package com.aooled_laptop.sqliteadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
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
    private boolean isDivPage;
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

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
                    if(currentpPage < pageNum){
                        currentpPage++;
                        totalList.addAll(DbManger.getListByCurrentPage(db, Constant.TABLE_NAME, currentpPage, pageSize));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) == totalItemCount);
            }
        });
    }
}

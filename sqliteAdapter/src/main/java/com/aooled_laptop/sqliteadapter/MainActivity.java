package com.aooled_laptop.sqliteadapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;


import com.aooled_laptop.bean.Person;
import com.aooled_laptop.utils.Constant;
import com.aooled_laptop.utils.DbManger;
import com.aooled_laptop.utils.MySqliteHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MySqliteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = DbManger.getIntance(this);
    }

    public void createDB(View view){

        /**
         * getReadableDatabase()
         * getWritableDatabase()
         * 创建或者打开数据库
         * 如果发现没有数据库, 就创建数据库
         * 如果发现有数据库, 会直接打开和使用数据库
         * 默认情况下, 这两个函数都是可读可写的数据库对象
         * 但是出现特殊情况下, 例如磁盘已满的情况下
         */
        SQLiteDatabase db = helper.getWritableDatabase();
    }

    public void click (View view) {

        switch (view.getId()){
            case R.id.btn_insert:
                SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "insert into person('" + Constant.NAME + "','" + Constant.AGE + "') values('zhangsan', 20)";
                DbManger.execSQL(db, sql);
                String sql2 = "insert into " + Constant.TABLE_NAME  + "('" + Constant.NAME + "','" + Constant.AGE + "') values('lisi', 25)";
                DbManger.execSQL(db, sql2);
                db.close();
                break;
            case R.id.btn_updata:
                db = helper.getWritableDatabase();
                String updateSql = "update " + Constant.TABLE_NAME + " set " + Constant.NAME + "='xiaoming' where " + Constant._ID + "=1";
                DbManger.execSQL(db, updateSql);
                db.close();
                break;
            case R.id.btn_delete:
                db = helper.getWritableDatabase();
                String delSql = "delete from " + Constant.TABLE_NAME + " where " + Constant._ID + "=2";
                DbManger.execSQL(db, delSql);
                db.close();
                break;
            case R.id.btn_query:
                db = helper.getWritableDatabase();
                String selectSql = "select * from " + Constant.TABLE_NAME;
                Cursor cursor = DbManger.selectDataBySql(db, selectSql, null);
                List<Person> list = DbManger.cursorToList(cursor);
                for (Person p : list){
                    Log.i("tag", p.toString());
                }
                db.close();
                break;
        }
    }

     public void loadDatabase(View view) {
        startActivity(new Intent(this, CursorAdapterActivity.class));
    }
}

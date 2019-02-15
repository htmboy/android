package com.aooled_laptop.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteOpenHelper
 * 1, 提供了onCreate() onUpgrade() 等创建数据库更新数据库的方法
 * 2, 提供了获取数据库对象的函数
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    /**
     *
     * @param context 上下文对象
     * @param name 表示创建数据库的名称
     * @param factory 游标工厂
     * @param version 表示创建数据库的版本 >= 1
     */
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    /**
     * 当数据库创建时回调的函数
     * @param db 数据库对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("tag", "-----onCreate-----");

        String sql =
                "create table " + Constant.TABLE_NAME +
                        "(" + Constant._ID + " Integer primary key autoincrement," +
                        Constant.NAME + " varchar(10)," + Constant.AGE + " Integer)";

        db.execSQL(sql); // 执行sql语句
    }

    /**
     * 当数据库版本更新时回调的函数
     * @param db 数据库对象
     * @param oldVersion 数据库旧版本
     * @param newVersion 数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("tag", "-----onUpgrade-----");

    }

    /**
     * 当数据库打开时回调的函数
     * @param db 数据库对象
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i("tag", "-----onOpen-----");
    }
}

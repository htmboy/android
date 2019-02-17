package com.aooled_laptop.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aooled_laptop.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class DbManger {
    private static MySqliteHelper helper;
    public static MySqliteHelper getIntance(Context context) {
        if(helper == null)
            helper = new MySqliteHelper(context);
        return helper;
    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db 数据库对象
     * @param sql sql语句
     */
    public static void execSQL(SQLiteDatabase db, String sql){

        if(db != null) {
            if (sql != null && !"".equals(sql))
                db.execSQL(sql);
        }
    }

    /**
     * 根据sql语句查询获得cursor对象
     * @param db 数据库对象
     * @param sql 查询的SQL语句
     * @param selectionArgs 查询条件的占位符
     * @return 查询结果
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs) {
        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(sql, selectionArgs);
        return cursor;
    }

    public static List<Person> cursorToList(Cursor cursor) {
        List<Person> list = new ArrayList<>();
        while(cursor.moveToNext()){
            // 根据参数中的字段名获取字段下标
            int columnIndex = cursor.getColumnIndex(Constant._ID);
            // 根据下标获取对应的值
            int _id = cursor.getInt(columnIndex);
            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));
            Person person = new Person(_id, name, age);

            list.add(person);

        }
        return list;
    }
}

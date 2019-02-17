package com.aooled_laptop.sqliteadapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aooled_laptop.utils.Constant;

import java.io.File;

public class CursorAdapterActivity extends AppCompatActivity {
    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sqlite_layout);
        lv = findViewById(R.id.lv);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "info.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery("select * from " + Constant.TABLE_NAME, null);
        MyCursorAdapter adapter = new MyCursorAdapter(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adapter);
    }

    /**
     * 以内部类的形式定义适配器
     */

    public class MyCursorAdapter extends CursorAdapter{

        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        /**
         * 表示创建适配器控件中每个item对应的view对象
         * @param context 上下文
         * @param cursor
         * @param parent 当前item的父布局
         * @return 每项item的view对象
         */

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(CursorAdapterActivity.this).inflate(R.layout.list_layout, null);
            return view;
        }

        /**
         * 通过newView() 方法确定了每个item展示的view对象 在bindView()中对方布局中的控件进行填充
         * @param view 有newView() 返回的每项view对象
         * @param context
         * @param cursor 数据源对象
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView tv_id = view.findViewById(R.id.tv_id);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_age = view.findViewById(R.id.tv_age);

            tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant._ID)) + "");
            tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant.NAME)));
            tv_age.setText(cursor.getInt(cursor.getColumnIndex(Constant.AGE)) + "");
        }
    }
}

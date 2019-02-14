package com.aooled_laptop.asynctaskintro;

import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    /****
     * 调用的顺序为
     * onPreExcute
     *    |
     * doInBackground
     *    |
     * onProgressUpdate (这个方法需要在doInBackground() 方法体内使用publishProgress() 才会调用该方法)
     *    |
     * onPostExcute
     *
     * @param voids
     * @return
     */
    @Override
    // 必须重写, 异步执行后台线程将要完成的任务
    protected Void doInBackground(Void... voids) {
        Log.d("xys", "doInBackground");
        return null;
    }

    @Override
    // 执行后台耗时操作前被调用, 通常用户完成一些初始化操作
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("xys", "onPreExecute");

    }

    @Override
    // 当doInBackground() 完成后, 系统会自动调用. 并将doInBackground() 的放回值传给该方法
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("xys", "onPostExecute");

    }

    @Override
    // 在doInBackground() 方法中调用publicProgress() 方法才能调用该方法
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("xys", "onProgressUpdate");

    }
}

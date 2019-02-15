package com.aooled_laptop.asynctaskintro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgressBarTest extends Activity {

    private ProgressBar mProgressBar;
    private MyAsyncTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        mProgressBar = findViewById(R.id.progressBar);
        mTask = new MyAsyncTask();
        mTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING)
            // cancel()方法只是将AsyncTask标记为cancel状态, 并不是真正的取消线程的执行.
            mTask.cancel(true);
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // 模拟进度更新
            for (int i = 0; i < 100; i++){
                // isCancelled() 是判断是否取消执行
                if(isCancelled())
                    break;
                publishProgress(i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(isCancelled())
                return;
            // 获取进度更新值
            mProgressBar.setProgress(values[0]);
        }
    }
}

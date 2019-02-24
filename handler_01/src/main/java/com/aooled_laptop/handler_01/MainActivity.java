package com.aooled_laptop.handler_01;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * handler 的三种用法
 * 1, 执行线程
 * 2, 传递消息和处理消息
 * 3, 处理回调函数
 *
 * handler 内部会跟looper进行关联
 * handler 负责发送消息, Looper负责接收handler发送的消息, 并直接把消息回传给handler自己
 * MessageQueue 就是一个存储消息的容器
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    private ImageView imageView;
    private int images[] = new int[]{R.mipmap.image4, R.mipmap.image3, R.mipmap.image1};
    private int index;
    private MyRunable myRunable = new MyRunable();

    @Override
    public void onClick(View v) {
//        handler.removeCallbacks(myRunable);
        handler.sendEmptyMessage(1);
    }


    class Person{
        public int age;
        public String name;
        public String toString(){
            return "name = " + name + " age = " + age;
        }
    }

    class MyRunable implements Runnable{

        @Override
        public void run() {
            index++;
            index = index%3;
            imageView.setImageResource(images[index]);
            // 每个1秒执行
            // 调用自身的方法
            handler.postDelayed(myRunable, 1000);
        }
    }
//    private Handler handler = new Handler(){
//
//    @Override
//        public void handleMessage(Message msg) {
//        // 主线程发送数据, 这里接收处理
//            textView.setText("" + msg.obj);
//            //textView.setText("" + msg.arg1 + "-" + msg.arg2);
//
//        }
//    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "" + 1, Toast.LENGTH_LONG).show();
            // 当返回false时, 则调用下面那个handleMessage. 然后又继续调用本函数. 直到返回为true;
            return false;
        }
    }){
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "" + 2, Toast.LENGTH_LONG).show();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        handler.postDelayed(myRunable, 1000);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
//                    Message message = new Message();
                    // 还可以同handler拿到message
                    Message message = handler.obtainMessage();
                    message.arg1 = 88;
                    message.arg2 = 100;
                    Person person = new Person();
                    person.age = 23;
                    person.name = "htmboy";
                    message.obj = person;
//                    handler.sendMessage(message);
                    // 还可以通过自身发送消息给hanlder
                    message.sendToTarget();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        // 子线程中不能更新ui
        // 需要借助handler
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            {
//                                // 借助handler 执行ui更新操作
//                                textView.setText("Updata thread");
//                            }
//                        }
//                    });
//                    // textView.setText("Updata thread"); 子线程中不能更新ui
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}

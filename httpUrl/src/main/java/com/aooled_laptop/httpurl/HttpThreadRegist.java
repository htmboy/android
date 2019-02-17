package com.aooled_laptop.httpurl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class HttpThreadRegist extends Thread{
    String url;
    String name;
    String age;

    public HttpThreadRegist(String url, String name, String age){
        this.url = url;
        this.name = name;
        this.age = age;
    }

    private void doGet(){

        url = url + "?name=" + name + "&age=" + age;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while((str = reader.readLine()) !=null){
                sb.append(str);
            }
            System.out.println("result:" + sb.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void doPost(){
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            OutputStream out = conn.getOutputStream();
            String content = "name=" + name + "&age=" + age;
            // post 是通过write的方式传递参数
            out.write(content.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while((str = reader.readLine()) != null) {
                sb.append(str);
            }
            System.out.println(sb.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        doPost();
    }
}

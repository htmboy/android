package com.aooled_laptop.httpurl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadThread extends Thread {
    private String fileName;
    private String url;

    public UploadThread(String url, String fileName){
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public void run() {
        super.run();

        String boundary = "---------------------------7de2c25201d48";
        String prefix = "--";
        String end = "\r\n";
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(prefix + boundary + end);
            // 对应服务器的文件上传参数
            out.writeBytes("Content-Disposition:form-data:" + "name = \"file\";filename=\"" + "Sky.jpg" + "\"" + end);
            out.writeBytes(end);
            // 写实体数据
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            byte[] b = new byte[1024 * 4];
            int len;
            while((len = fileInputStream.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.writeBytes(end);
            out.writeBytes(prefix + boundary + prefix + end);
            out.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null){
                sb.append(str);
            }
            System.out.println("response:" + sb.toString());
            if (out != null)
                out.close();
            if(reader != null)
                reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

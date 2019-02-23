package com.aooled_laptop.httpupload.task;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamBinary implements Binary {

    private InputStream mInputStream;
    private String fileName;
    private String mimeType;

    public InputStreamBinary(InputStream mInputStream, String fileName, String mimeType) {
        // 拿到输入流的长度, 只有两种输入流可以拿到
        // ByteArrayInputStream
        // FileOutputStream
        if (!(mInputStream instanceof ByteArrayInputStream) && !(mInputStream instanceof FileInputStream))
            throw new IllegalArgumentException("InputStream is not supported!");
        else if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(mimeType))
            throw new IllegalArgumentException("The argument can be not null");
        this.mInputStream = mInputStream;
        this.fileName = fileName;
        this.mimeType = mimeType;


    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public long getBinaryLength() {
        try {
            return mInputStream.available();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onWriteBinary(OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[2048];
        int len;
        while ((len = mInputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }
    }
}

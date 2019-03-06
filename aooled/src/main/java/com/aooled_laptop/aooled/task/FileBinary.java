package com.aooled_laptop.aooled.task;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileBinary implements Binary {

    private File file;

    private int what;
    private OnBinaryProgressListener mProgressListener;

    public FileBinary(File file) {
        if (file == null || !file.exists())
            throw new IllegalArgumentException("The file is not found");
        this.file = file;
    }

    // 设置上传进度监听
    public void setProgressListener(int what, OnBinaryProgressListener progressListener){
        this.what = what;
        this.mProgressListener = progressListener;
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public String getMimeType() {
        String mimeType = "application/octet-stream";
        if(MimeTypeMap.getSingleton().hasExtension(getFileName())){
            String extension = MimeTypeMap.getFileExtensionFromUrl(getFileName());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }

    @Override
    public long getBinaryLength() {
        return file.length();
    }

    @Override
    public void onWriteBinary(OutputStream outputStream) throws IOException {
        long allLength = getBinaryLength();
        long hasUploadLength = 0;
        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[2048];
        int len;
        while ((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
            hasUploadLength += len;
            int progress = (int) (hasUploadLength / allLength * 100);
            Poster.getInstance().post(new ProgressMessage(ProgressMessage.CMD_PROGRESS, what, progress, mProgressListener));
        }
    }





}

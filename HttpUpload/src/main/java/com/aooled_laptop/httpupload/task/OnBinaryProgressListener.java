package com.aooled_laptop.httpupload.task;

public interface OnBinaryProgressListener {

    void onProgress(int what, int progress);

    void onError(int what);
}

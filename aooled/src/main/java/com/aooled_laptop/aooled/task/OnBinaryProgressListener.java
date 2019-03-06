package com.aooled_laptop.aooled.task;

public interface OnBinaryProgressListener {

    void onProgress(int what, int progress);

    void onError(int what);
}

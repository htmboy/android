package com.aooled_laptop.httpupload.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

public class CounterOutputStream extends OutputStream {
    private AtomicLong mAtomicLong = new AtomicLong();

    /**
     * 拿到总长度
     * @return
     */
    public long get(){
        return mAtomicLong.get();
    }

    public void write(long b) throws IOException {
        mAtomicLong.addAndGet(b);
    }

    @Override
    public void write(int b) throws IOException {
        mAtomicLong.addAndGet(1);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        mAtomicLong.addAndGet(b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        mAtomicLong.addAndGet(len);
    }

}

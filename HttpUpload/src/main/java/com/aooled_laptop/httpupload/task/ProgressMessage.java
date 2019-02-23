package com.aooled_laptop.httpupload.task;

public class ProgressMessage implements Runnable{
    public static final int CMD_ERROR = 1;
    public static final int CMD_PROGRESS = 2;
    private int cmd;
    private int what;
    private int progress;
    private OnBinaryProgressListener mProgressListener;

    public ProgressMessage(int cmd, int what, int progress, OnBinaryProgressListener mProgressListener) {
        this.cmd = cmd;
        this.what = what;
        this.progress = progress;
        this.mProgressListener = mProgressListener;
    }

    public ProgressMessage(int cmd, int what, OnBinaryProgressListener mProgressListener) {
        this.cmd = cmd;
        this.what = what;
        this.mProgressListener = mProgressListener;
    }

    @Override
    public void run() {
        switch (cmd){
            case CMD_ERROR:
                if (mProgressListener != null)
                    mProgressListener.onError(what);
                break;
            case CMD_PROGRESS:
                if (mProgressListener != null)
                    mProgressListener.onProgress(what, progress);
                break;
        }
    }
}

package com.aooled_laptop.httpupload.task;

import java.io.IOException;
import java.io.OutputStream;

public interface Binary {

    String getFileName();

    String getMimeType();

    long getBinaryLength();

    void onWriteBinary(OutputStream outputStream) throws IOException;
}

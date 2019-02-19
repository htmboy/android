package com.aooled_laptop.httpupload.task;

import java.io.File;

public class KeyValue {
    private String key;
    private Object value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(String key, File value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public Object getValue() {
        return value;
    }

    public String toString(){
        return "key=" + key + "; value=" + value;
    }

}

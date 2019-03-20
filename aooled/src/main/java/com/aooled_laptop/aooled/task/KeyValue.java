package com.aooled_laptop.aooled.task;

public class KeyValue {
    private String key;
    private Object value;

    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue(String key, Binary value) {
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

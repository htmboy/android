package com.aooled_laptop.aooled.task;

public enum RequestMethod {
    GET("GET"), POST("POST"), JSON("JSON"), HEAD("HEAD");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }

    public String toString(){
        return value;
    }

    public boolean isOutputMethod(){
        switch(this){
            case POST:
                return true;
            case JSON:
                return true;
            default:
                return false;
        }
    }
}

package com.android.ql.restaurant.data;

public class BaseNetResult {

    public BaseNetResult(String code, Object obj) {
        this.code = code;
        this.obj = obj;
    }

    public String code;
    public Object obj;
}
package com.android.base.androidbaseproject.data;

/**
 * Base data structure
 * Created by secretqi on 2018/3/1.
 */

public class BaseData<T> {

    private int statusCode;

    private String message;

    private T content;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

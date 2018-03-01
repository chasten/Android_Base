package com.android.base.androidbaseproject.exception;

/**
 * Server Exception
 * Created by secretqi on 2018/3/1.
 */

public class ServerException extends RuntimeException {

    public int code;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
    }
}
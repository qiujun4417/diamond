package com.wonders.diamond.core.exceptions;

/**
 * Created by nick on 2016/12/30.
 */
public class BaseException extends RuntimeException{

    private int code;
    private String msg;
    private Throwable ex;

    public BaseException(){

    }

    public BaseException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(int code, String msg, Throwable ex){
        super(msg, ex);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(Throwable ex){
        super(ex);
    }

    public BaseException(String msg, Throwable ex){
        super(msg, ex);
        this.msg = msg;
    }
}

package com.wonders.diamond.core.netty.exception;

/**
 * Created by nick on 2016/5/21.
 * @author nick
 */
public class NettyServerStartUpException extends RuntimeException{

    public NettyServerStartUpException(String message, Throwable ex){
        super(message,ex);
    }
}

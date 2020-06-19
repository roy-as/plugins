package com.star.Exception;

import java.util.function.Supplier;

public class NettyConfigErrorException extends RuntimeException implements Supplier<NettyConfigErrorException> {

    private int code;
    private String msg;

    public NettyConfigErrorException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NettyConfigErrorException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public NettyConfigErrorException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    @Override
    public NettyConfigErrorException get() {
        return this;
    }
}

package com.star.common.exception;

import java.util.function.Supplier;

/**
 * 自定义异常
 */
public class MessageErrorException extends RuntimeException implements Supplier<MessageErrorException> {

	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public MessageErrorException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public MessageErrorException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public MessageErrorException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public MessageErrorException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public MessageErrorException get() {
		return this;
	}
}

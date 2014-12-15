package com.xalmiento.desknet.checkstyle.http;

public class HttpResponse<T> {
	private int code;
	private T body;
	public HttpResponse(int code, T body) {
		super();
		this.code = code;
		this.body = body;
	}
	public int getCode() {
		return code;
	}
	public T getBody() {
		return body;
	}
}

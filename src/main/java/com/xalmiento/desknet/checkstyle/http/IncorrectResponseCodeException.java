package com.xalmiento.desknet.checkstyle.http;

import java.io.IOException;

@SuppressWarnings("serial")
public class IncorrectResponseCodeException extends IOException{

	public IncorrectResponseCodeException(String message) {
		super(message);
	}

	public IncorrectResponseCodeException(int code) {
		super(String.valueOf(code));
	}

}

package com.xalmiento.desknet.checkstyle.http.parsing;

import java.io.IOException;

@SuppressWarnings("serial")
public class ParsingException extends IOException{

	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParsingException(Throwable cause) {
		super(cause);
	}

}

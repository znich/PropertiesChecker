package com.xalmiento.desknet.checkstyle.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.xalmiento.desknet.checkstyle.http.parsing.Parser;

public class StringParser implements Parser<String>{

	public String parse(InputStream is) throws IOException {
		String encoding = "UTF-8";

		Reader reader = new InputStreamReader(is, encoding);
		StringBuilder builder = new StringBuilder();
		int c;
		while ((c = reader.read()) > -1) {
			builder.append((char) c);
		}
		return builder.toString();
	}

}

package com.xalmiento.desknet.checkstyle.http.parsing;

import java.io.IOException;
import java.io.InputStream;

public interface Parser<T> {
	T parse(InputStream is) throws ParsingException, IOException;
}

package com.xalmiento.desknet.checkstyle.http;

import java.io.IOException;
import java.io.InputStream;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xalmiento.desknet.checkstyle.http.parsing.Parser;
import com.xalmiento.desknet.checkstyle.http.parsing.ParsingException;

public class JsonParser<T> implements Parser<T> {
	private Class<T> clazz;
	
	public JsonParser(Class<T> clazz){
		this.clazz = clazz;
	}

	public T parse(InputStream is) throws IOException {
		ObjectMapper mapper = new ObjectMapper();	
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(is, clazz);
		} catch (JsonParseException e) {
			throw new ParsingException(e);
		} catch (JsonMappingException e) {
			throw new ParsingException(e);
		}
	}
}

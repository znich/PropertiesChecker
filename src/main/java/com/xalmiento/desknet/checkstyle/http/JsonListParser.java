package com.xalmiento.desknet.checkstyle.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xalmiento.desknet.checkstyle.http.parsing.Parser;
import com.xalmiento.desknet.checkstyle.http.parsing.ParsingException;

public class JsonListParser<V> implements Parser<List<V>> {
	private Class<V> clazz;
	
	public JsonListParser(Class<V> clazz){
		this.clazz = clazz;
	}

	@Override
	public List<V> parse(InputStream is) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JavaType typeReference = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		try {
			return mapper.readValue(is, typeReference);
		} catch (JsonParseException e) {
			throw new ParsingException(e);
		} catch (JsonMappingException e) {
			throw new ParsingException(e);
		}
	}

}

package com.xalmiento.desknet.checkstyle.http;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.xalmiento.desknet.checkstyle.http.parsing.Parser;
import com.xalmiento.desknet.checkstyle.http.parsing.ParsingException;

public class XmlParser<T> implements Parser<T>{
	private Class<T> clazz;
	
	public XmlParser(Class<T> clazz){
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T parse(InputStream is) throws IOException {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T)unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw new ParsingException(e);
		}
		
		
	}

}

package com.xalmiento.desknet.checkstyle.phraseapp.impl;

import com.xalmiento.desknet.checkstyle.phraseapp.model.Property;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppResourcesAPIService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.01.2015 16:19
 *
 * @author a.laguta
 */
public class PhraseAppResourcesAPIServiceImpl
        extends PhraseAppAPIServiceImpl<Property> implements PhraseAppResourcesAPIService {

    public static final String URL = "translations/download";

    public PhraseAppResourcesAPIServiceImpl(String token) {
        super(token);
    }

    @Override
    public List<Property> getProperties(String tag) {
        Map<String, String> parameters  = new HashMap<String, String>();
        parameters.put("tag", tag);
        parameters.put("locale", "en");
        parameters.put("format", "xml");
        List<Property> properties = getResource(URL, parameters, null).getProperties();
        return properties != null ? properties : Collections.<Property>emptyList();
    }
}

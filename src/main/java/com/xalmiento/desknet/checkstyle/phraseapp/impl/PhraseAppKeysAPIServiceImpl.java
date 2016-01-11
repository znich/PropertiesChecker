package com.xalmiento.desknet.checkstyle.phraseapp.impl;

import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppAPIServiceImplV2;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 11:00
 *
 * @author a.laguta
 */
public class PhraseAppKeysAPIServiceImpl extends PhraseAppAPIServiceImplV2<Key>
        implements PhraseAppKeysAPIService {

    private static final String KEYS_URL = "keys";

    public PhraseAppKeysAPIServiceImpl(String token, String projectId) {
        super(token, projectId);
    }

    @Override
    public List<Key> getKeys() {
        return getList(KEYS_URL, Key.class, new HashMap<String, String>(), null);
    }

    @Override
    public List<Key> getKeys(Set<String> keys) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put(QUERY_PARAM, createKeyNameSearchQuery(keys));

        return getList(KEYS_URL, Key.class, parameters, null,  null);
    }

    private String createKeyNameSearchQuery(Set<String> keys) {
        StringBuilder sb = new StringBuilder();
        sb.append("name:");
        for (String key : keys) {
            sb.append(key);
            sb.append(",");
        }
        return sb.toString();
    }

    @Override
    public List<Key> getKeys(String tagName) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put(QUERY_PARAM, createTagNameSearchQuery(tagName));

        return getList(KEYS_URL, Key.class, parameters, null,  null);
    }

    private String createTagNameSearchQuery(String tagName) {
        return "tags:" + tagName;
    }

}

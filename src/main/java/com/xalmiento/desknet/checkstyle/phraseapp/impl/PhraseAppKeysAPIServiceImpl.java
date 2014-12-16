package com.xalmiento.desknet.checkstyle.phraseapp.impl;

import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 11:00
 *
 * @author a.laguta
 */
public class PhraseAppKeysAPIServiceImpl extends PhraseAppAPIServiceImpl<Key>
        implements PhraseAppKeysAPIService {

    private static final String KEYS_URL = "translation_keys";

    public PhraseAppKeysAPIServiceImpl(String token) {
        super(token);
    }

    @Override
    public List<Key> getKeys() {
        return getList(KEYS_URL, Key.class, new HashMap<String, String>(), null);
    }

    @Override
    public List<String> getKeys(String... keys) {
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("translation_keys", keys);
        List<Key> list = getList(KEYS_URL, Key.class, new HashMap<String, String>(), params,  null);

        List<String> result = new ArrayList<String>(list.size());

        for (Key key : list) {
            result.add(key.getName());
        }

        return result;
    }

}

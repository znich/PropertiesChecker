package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.util.List;
import java.util.Set;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 10:43
 *
 * @author a.laguta
 */
public interface PhraseAppKeysAPIService extends PhraseAppAPIService<Key> {

    /**
     * Returns all keys
     *
     * @return
     */
    List<Key> getKeys();

    /**
     * Returns list of {@link com.xalmiento.desknet.checkstyle.phraseapp.model.Key}
     * with provided key names
     *
     * @param keys
     * @return
     */
    List<Key> getKeys(Set<String> keys);

    /**
     * Returns list of keys with provided tag name
     *
     * @param tagName
     * @return
     */
    List<Key> getKeys(String tagName);
}

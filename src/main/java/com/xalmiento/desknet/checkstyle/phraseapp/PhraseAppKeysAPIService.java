package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.util.List;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 10:43
 *
 * @author a.laguta
 */
public interface PhraseAppKeysAPIService extends PhraseAppAPIService<Key> {

    List<Key> getKeys();

    List<String> getKeys(String... keys);
}

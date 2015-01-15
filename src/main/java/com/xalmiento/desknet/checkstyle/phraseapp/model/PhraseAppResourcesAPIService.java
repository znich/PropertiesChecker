package com.xalmiento.desknet.checkstyle.phraseapp.model;

import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppAPIService;

import java.util.List;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.01.2015 16:18
 *
 * @author a.laguta
 */
public interface PhraseAppResourcesAPIService extends PhraseAppAPIService<Property> {

    List<Property> getProperties(String tag);
}

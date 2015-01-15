package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Property;

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

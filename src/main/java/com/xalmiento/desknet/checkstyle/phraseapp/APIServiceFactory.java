package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppKeysAPIServiceImpl;
import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppResourcesAPIServiceImpl;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 16.12.2014 10:27
 *
 * @author a.laguta
 */
public class APIServiceFactory {

    private PhraseAppKeysAPIService keysAPIService;
    private PhraseAppResourcesAPIService resourcesAPIService;

    public APIServiceFactory() {
    }

    public PhraseAppKeysAPIService getKeysService(String token) {
          if (keysAPIService == null) {
              keysAPIService = new PhraseAppKeysAPIServiceImpl(token);
          }
        return keysAPIService;
    }

    public PhraseAppResourcesAPIService getResourcesService(String token) {
        if (resourcesAPIService == null) {
            resourcesAPIService = new PhraseAppResourcesAPIServiceImpl(token);
        }
        return resourcesAPIService;
    }
}

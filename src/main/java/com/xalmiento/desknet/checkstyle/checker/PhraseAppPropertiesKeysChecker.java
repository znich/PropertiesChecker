package com.xalmiento.desknet.checkstyle.checker;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.xalmiento.desknet.checkstyle.http.StringParser;
import com.xalmiento.desknet.checkstyle.phraseapp.APIServiceFactory;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 03.12.2014 22:25
 *
 * @author Anatoly
 */
public class PhraseAppPropertiesKeysChecker extends AbstractPropertiesKeysChecker {

    private String phraseAppToken;

    private APIServiceFactory apiServiceFactory;

    private static final String MISSING_PROPERTY_MESSAGE =
            "Property key \"%s\" in %s is missing at PhraseApp.";

    public PhraseAppPropertiesKeysChecker(String phraseAppToken) {
        this.phraseAppToken = phraseAppToken;
    }

    @Override
    public LocalizedMessages processCheck(File file, List<String> lines) {
        initServiceFactory();
        findMissingProperty(file, lines);
        return getMessageCollector();
    }

    private void findMissingProperty(File file, List<String> lines){
        try {
            Set<String> existKeys = getKeys(file);
            PhraseAppKeysAPIService keysService = apiServiceFactory.getKeysService(phraseAppToken);

            List<String> keys = keysService.getKeys(existKeys.toArray(new String[existKeys.size()]));

            for (String exist : existKeys) {
                if (keys == null || !keys.contains(exist)) {
                    log(findRowNumber(exist, lines), String.format(
                            MISSING_PROPERTY_MESSAGE, exist, file.getName()));
                }
            }
        } catch (IOException e) {
            log(0, "unable.open", file.getPath());
        }

        getMessageCollector();
    }

    private void initServiceFactory() {
        if (apiServiceFactory == null) {
            apiServiceFactory = new APIServiceFactory();
        }

    }
}

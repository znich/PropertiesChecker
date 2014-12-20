package com.xalmiento.desknet.checkstyle.checker;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.xalmiento.desknet.checkstyle.phraseapp.APIServiceFactory;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String WRONG_TAG_MESSAGE =
            "Property key \"%s\" has not the required tag name \"%s\".";

    public static final String TAD_REGEXP = "^((?!Msg).*)Msg\\.properties$";

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

            List<Key> keys = keysService.getKeys(existKeys.toArray(
                    new String[existKeys.size()]));
            List<String> keysNames = getKeysNames(keys);

            for (String exist : existKeys) {
                if (keysNames == null || !keysNames.contains(exist)) {
                    log(findRowNumber(exist, lines), String.format(
                            MISSING_PROPERTY_MESSAGE, exist, file.getName()));
                }
            }

            String currentFileTag = parseCurrentFileTag(file);

            for (Key key : keys) {
                if (!key.hasTag(currentFileTag)) {
                    String name = key.getName();
                    log(findRowNumber(name, lines), String.format(
                            WRONG_TAG_MESSAGE, name, currentFileTag));
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

    private List<String> getKeysNames(Collection<Key> keys) {
        if (keys == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for (Key key: keys) {
            result.add(key.getName());
        }
        return result;
    }

    private String parseCurrentFileTag(File file) {
        Matcher matcher = Pattern.compile(TAD_REGEXP).matcher(file.getName());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    //testing method
    public static void main(String[] args) {
        String s = parseCurrentFileTag2("CommonMsg.properties");
        System.out.println(s);
    }

    private static String parseCurrentFileTag2(String file) {
        Matcher matcher = Pattern.compile(TAD_REGEXP).matcher(file);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

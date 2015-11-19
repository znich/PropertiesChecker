package com.xalmiento.desknet.checkstyle.checker;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.xalmiento.desknet.checkstyle.phraseapp.APIServiceFactory;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppResourcesAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Property;

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

    public static final String MISSING_PROPERTY_MESSAGE =
            "Property key \"%s\" in %s is missing at PhraseApp.";

    public static final String WRONG_TAG_MESSAGE =
            "Property key \"%s\" has not the required tag name \"%s\".";

    public static final String DELETED_KEY_MESSAGE =
            "Property key \"%s\" was deleted from %s, but it still exists at PhraseApp.";

    public static final String AJAX_PROPERTIES_SUFFIX = "Msg";

    public static final String AJAX_TAG_REGEXP = "^((?!" + AJAX_PROPERTIES_SUFFIX + ").*)"
            + AJAX_PROPERTIES_SUFFIX +"\\.properties$";

    public static final String APPLICATION_TAG_REGEXP = "^((?!\\.properties).*)\\.properties$";

    private String phraseAppToken;
    private APIServiceFactory apiServiceFactory;

    public PhraseAppPropertiesKeysChecker(String phraseAppToken) {
        this.phraseAppToken = phraseAppToken;
    }

    @Override
    public LocalizedMessages processCheck(File file, List<String> lines) {
        initServiceFactory();
        findMissingProperty(file, lines);
        findDeletedProperty(file);
        return getMessageCollector();
    }

    private void initServiceFactory() {
        if (apiServiceFactory == null) {
            apiServiceFactory = new APIServiceFactory();
        }
    }

    private void findMissingProperty(File file, List<String> lines){
        try {
            Set<String> existKeys = getKeys(file);
            PhraseAppKeysAPIService keysService = apiServiceFactory.getKeysService(phraseAppToken);

            List<Key> keys = keysService.getKeys(existKeys.toArray(new String[existKeys.size()]));
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
        String name = file.getName();
        Matcher matcher = name.contains(AJAX_PROPERTIES_SUFFIX)
                ? Pattern.compile(AJAX_TAG_REGEXP).matcher(name)
                : Pattern.compile(APPLICATION_TAG_REGEXP).matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void findDeletedProperty(File file) {
        try {
            Set<String> existKeys = getKeys(file);
            PhraseAppResourcesAPIService resourcesService =
                    apiServiceFactory.getResourcesService(phraseAppToken);

            List<Property> properties = resourcesService.getProperties(parseCurrentFileTag(file));

            for (Property property : properties) {
                String key = property.getKey();
                if (!existKeys.contains(key)) {
                    log(0, String.format(DELETED_KEY_MESSAGE, key, file.getName()));
                }
            }
        } catch (IOException e) {
            log(0, "unable.open", file.getPath());
        }
    }

    //test method
    public static void main(String[] args) {
        /*String s = parseCurrentFileTag2("CommonMsg.properties");
        System.out.println(s);*/
     /*   PhraseAppResourcesAPIService resourcesAPIService = new PhraseAppResourcesAPIServiceImpl(
                "3bc90a9df273f51636dc00ff47226df8");
        List<Property> common = resourcesAPIService.getProperties("Common");*/
        System.out.println(parseCurrentFileTag2("Common.properties"));
    }

    private static String parseCurrentFileTag2(String file) {
        Matcher matcher = Pattern.compile(APPLICATION_TAG_REGEXP).matcher(file);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

package com.xalmiento.desknet.checkstyle.checker;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.xalmiento.desknet.checkstyle.phraseapp.APIServiceFactory;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppAPIProjectServiceImpl;
import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppKeysAPIServiceImpl;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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

    private static final String MISSING_PROPERTY_MESSAGE =
            "Property key \"%s\" in %s is missing at PhraseApp.";

    public static final String WRONG_TAG_MESSAGE =
            "Property key \"%s\" has not the required tag name \"%s\".";


    public static final String DELETED_KEY_MESSAGE =
            "Property key \"%s\" was deleted from %s, but it still exists at PhraseApp.";

    public static final String AJAX_PROPERTIES_SUFFIX = "Msg";

    public static final String APPLICATION_TAG_REGEXP = "^((?!\\.properties).*)\\.properties$";

    private static final String AJAX_TAG_REGEXP = "^((?!" + AJAX_PROPERTIES_SUFFIX + ").*)"
            + AJAX_PROPERTIES_SUFFIX + "\\.properties$";

    private String phraseAppToken;
    private APIServiceFactory apiServiceFactory;


    public PhraseAppPropertiesKeysChecker(String phraseAppToken, String projectName) {
        this.phraseAppToken = phraseAppToken;

        initServiceFactory(projectName);
    }

    private void initServiceFactory(String projectName) {
        if (apiServiceFactory == null) {
            apiServiceFactory = new APIServiceFactory(projectName);
        }
    }

    @Override
    public LocalizedMessages processCheck(File file, List<String> lines) {
        try {
            Set<String> existKeys = getKeys(file);
            findMissingProperty(file, lines, existKeys);
            findDeletedProperty(file, existKeys);
        } catch (IOException e) {
            log(0, "unable.open", file.getPath());
        }
        return getMessageCollector();
    }

    private void findMissingProperty(File file, List<String> lines, Set<String> existKeys) {
        PhraseAppKeysAPIService keysService = apiServiceFactory.getKeysService(phraseAppToken);

        List<Key> keys = keysService.getKeys(existKeys);

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

    }

    private List<String> getKeysNames(Collection<Key> keys) {
        if (keys == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for (Key key : keys) {
            result.add(key.getName());
        }
        return result;
    }

    private void findDeletedProperty(File file, Set<String> existKeys) {
        String currentFileTag = parseCurrentFileTag(file);
        List<Key> keys = apiServiceFactory.getKeysService(phraseAppToken).getKeys(currentFileTag);

        for (Key key : keys) {
            if (!existKeys.contains(key.getName())) {
                log(0, String.format(DELETED_KEY_MESSAGE, key.getName(), file.getName()));
            }
        }
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

    //test method
    public static void main(String[] args) {
        String token = "";
        PhraseAppAPIProjectServiceImpl resourcesAPIService = new PhraseAppAPIProjectServiceImpl(
                token);


        PhraseAppAPIProjectServiceImpl service = new PhraseAppAPIProjectServiceImpl(token);
        PhraseAppKeysAPIService service1 = new PhraseAppKeysAPIServiceImpl(token, "");
        HashSet<String> keys1 = new HashSet<String>();
        List<Key> keys = service1.getKeys(keys1);

        System.out.println(keys);
    }
}

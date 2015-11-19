package com.xalmiento.desknet.checkstyle.checker;

import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 03.12.2014 22:25
 *
 * @author Anatoly
 */
public class DuplicatePropertiesKeysChecker extends AbstractPropertiesKeysChecker {

    public static final String DUPLICATE_PROPERTY_MESSAGE =
            "You have a duplicate property key \"%s\" in %s and %s. "
                    + "Try to reuse an exist property or rename one of them.";


    private HashMap<String, String> keysValues = new HashMap<String, String>();
    private List<String> exclusionList;

    public DuplicatePropertiesKeysChecker(String[] exclusions) {
        exclusionList = exclusions != null
                ? Arrays.asList(exclusions)
                : new ArrayList<String>();

    }

    @Override
    public LocalizedMessages processCheck(File file, List<String> lines) {
        try {
            Set<String> objects = getKeys(file);
            for (String key : objects) {
                String fileName = file.getName();
                if (exclusionList.contains(fileName)) {
                    continue;
                }
                String duplicate = keysValues.put(key, fileName);
                if (duplicate != null && !duplicate.equals(fileName)) {
                    log(findRowNumber(key, lines), String.format(
                            DUPLICATE_PROPERTY_MESSAGE,
                            key,
                            file.getName(),
                            duplicate));
                }
            }
        } catch (IOException e) {
            log(0, "unable.open", file.getPath());
        }

        return getMessageCollector();
    }

}

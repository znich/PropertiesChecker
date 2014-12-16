package com.xalmiento.desknet.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.xalmiento.desknet.checkstyle.phraseapp.APIServiceFactory;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppKeysAPIService;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Key;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 03.12.2014 22:25
 *
 * @author Anatoly
 */
public class DuplicatePropertiesKeysCheck extends AbstractFileSetCheck {

    private String directory;

    private String[] exclusions;

    private String token;

    private HashMap<String, Property> keysValues = new HashMap<String, Property>();

    private APIServiceFactory apiServiceFactory;

    private List<String> exclusionList = exclusions != null
            ? Arrays.asList(exclusions)
            : new ArrayList<String>();
    private static final String DUPLICATE_PROPERTY_MESSAGE =
            "You have a duplicate property key \"%s\" in %s and %s. "
                    + "Try to reuse an exist property or rename one of them.";
    private static final String MISSING_PROPERTY_MESSAGE =
            "Property key \"%s\" in %s is missing at PhraseApp.";
    private static final String PROPERTIES_FILE_REGEX = "[^_]*properties$";

    @Override
    protected void processFiltered(File file, List<String> lines) {
        initServiceFactory();
        if (checkFileDirectory(file)) {
            try {
                findDuplicates(file, lines);
                findMissingProperty(file, lines);
            } catch (IOException e) {
                log(0, "unable.open", file.getPath());
            }
        }
    }

    private void findDuplicates(File file, List<String> lines) throws IOException {
        Set<String> objects = getKeys(file);

        for (String key : objects) {
            if (exclusionList.contains(key)) {
                continue;
            }
            String fileName = file.getName();
            Property duplicate = keysValues.put(key, new Property(key, fileName));
            if (duplicate != null && duplicate.notCurrentFile(fileName)) {
                log(findRowNumber(key, lines), String.format(
                        DUPLICATE_PROPERTY_MESSAGE, key, file.getName(), duplicate.getFileName()));
            }
        }
    }

    private Set<String> getKeys(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        return properties.stringPropertyNames();
    }

    private int findRowNumber(String key, List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line != null && line.startsWith(key)) {
                return i + 1;
            }
        }
        return 0;
    }

    private void findMissingProperty(File file, List<String> lines) throws IOException {
        if (token == null) {
            return;
        }
        Set<String> existKeys = getKeys(file);

        PhraseAppKeysAPIService keysService = apiServiceFactory.getKeysService(token);

        List<String> keys = keysService.getKeys(existKeys.toArray(new String[existKeys.size()]));

        for (String exist : existKeys) {
            if (keys == null || !keys.contains(exist)) {
                log(findRowNumber(exist, lines), String.format(
                        MISSING_PROPERTY_MESSAGE, exist, file.getName()));
            }
        }
    }

    private boolean checkFileDirectory(File file) {
        String path = file.getParent();
        String fileName = file.getName();
        return path.endsWith(directory) && fileName.matches(PROPERTIES_FILE_REGEX);
    }

    private void initServiceFactory() {
        if (apiServiceFactory == null) {
            apiServiceFactory = new APIServiceFactory();
        }

    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setExclusions(String[] exclusions) {
        this.exclusions = exclusions;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public class Property {
        private String key;
        private String fileName;

        public Property(String key, String fileName) {
            this.key = key;
            this.fileName = fileName;
        }

        private boolean notCurrentFile(String fileName) {
            return !getFileName().equals(fileName);
        }

        public String getKey() {
            return key;
        }

        public String getFileName() {
            return fileName;
        }
    }


}

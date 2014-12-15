package com.xalmiento.desknet.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;

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
 * Author : Anatoly
 * Created : 03.12.2014 22:25
 *
 * @author Anatoly
 */
public class DuplicatePropertiesKeysCheck extends AbstractFileSetCheck {

    private String directory;

    private String[] exclusions;

    private HashMap<String, Property> keysValues = new HashMap<String, Property>();

    private List<String> exclusionList = exclusions != null
            ? Arrays.asList(exclusions)
            : new ArrayList<String>();
    private final String MESSAGE = "You have a duplicate property key %s in %s and %s. "
            + "Try to reuse an exist property or rename one of them.";
    private final String PROPERTIES_FILE_REGEX = "[^_]*properties$";


    @Override
    protected void processFiltered(File file, List<String> lines) {
        if (checkFileDirectory(file)) {
            try {
                Property duplicate = findErrors(file);
                if (duplicate != null) {
                    String key = duplicate.getKey();
                    log(
                            findRowNumber(key, lines), String.format(
                                    MESSAGE, key, file.getName(), duplicate.getFileName()));
                }
            } catch (IOException e) {
                log(0, "unable.open", file.getPath());
            }

        }
    }

    private Property findErrors(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        Set<String> objects = properties.stringPropertyNames();

        for (String key : objects) {
            if (exclusionList.contains(key)) {
                continue;
            }
            String fileName = file.getName();
            Property duplicate = keysValues.put(key, new Property(key, fileName));
            if (duplicate != null && duplicate.notCurrentFile(fileName)) {
                return duplicate;
            }
        }
        return null;
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

    private boolean checkFileDirectory(File file) {
        String path = file.getParent();
        String fileName = file.getName();
        return path.endsWith(directory) && fileName.matches(PROPERTIES_FILE_REGEX);
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setExclusions(String[] exclusions) {
        this.exclusions = exclusions;
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

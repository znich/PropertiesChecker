package com.xalmiento.desknet.checkstyle.checker;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 17.12.2014 11:39
 *
 * @author a.laguta
 */
public abstract class AbstractPropertiesKeysChecker extends AbstractFileSetCheck {

    @Override
    protected void processFiltered(File aFile, List<String> aLines) {
        //nothing, it is never called
    }

    protected Set<String> getKeys(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        return properties.stringPropertyNames();
    }

    protected int findRowNumber(String key, List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line != null && checkPropertyKeyName(line, key)) {
                return i + 1;
            }
        }
        return 0;
    }

    private boolean checkPropertyKeyName(String line, String name) {
        name = name.replace(".", "\\.");
        String regexp = "^" + name + "[\\s+|=].*$";
        return line.matches(regexp);
    }

    public abstract LocalizedMessages processCheck(File file, List<String> lines);

    @Override
    protected Map<String, String> getCustomMessages() {
        return new HashMap<String, String>();
    }

}

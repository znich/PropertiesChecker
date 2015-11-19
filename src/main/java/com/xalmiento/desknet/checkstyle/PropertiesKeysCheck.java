package com.xalmiento.desknet.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.xalmiento.desknet.checkstyle.checker.DuplicatePropertiesKeysChecker;
import com.xalmiento.desknet.checkstyle.checker.PhraseAppPropertiesKeysChecker;
import com.xalmiento.desknet.checkstyle.phraseapp.APIServiceFactory;
import com.xalmiento.desknet.checkstyle.checker.CheckerFactory;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 03.12.2014 22:25
 *
 * @author Anatoly
 */
public class PropertiesKeysCheck extends AbstractFileSetCheck {

    private String[] directories;

    private String[] duplicateExclusions;

    private String phraseAppToken;

    private Boolean enablePhraseApp;

    private APIServiceFactory apiServiceFactory;

    private CheckerFactory checkerFactory;

    private static final String PROPERTIES_FILE_REGEX =
            "^((((?!Msg).*)Msg)|(ApplicationResources))\\.properties$";


    @Override
    protected void processFiltered(File file, List<String> lines) {
        if (validateFile(file)) {
            initServicesFactories();

            checkDuplicate(file, lines);
            checkPhraseApp(file, lines);
        }
    }

    private boolean validateFile(File file) {
        String path = file.getParent();
        String fileName = file.getName();
        return checkFileDirectory(path) && checkFileName(fileName);
    }

    private boolean checkFileDirectory(String path) {
        for (String directory : directories) {
            if (path.endsWith(directory)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFileName(String fileName) {
        return fileName.matches(PROPERTIES_FILE_REGEX);
    }

    private void initServicesFactories() {
        if (apiServiceFactory == null) {
            apiServiceFactory = new APIServiceFactory();
        }

        if (checkerFactory == null) {
            checkerFactory = new CheckerFactory();
        }
    }

    private void checkDuplicate(File file, List<String> lines) {
        DuplicatePropertiesKeysChecker duplicateChecker
                = checkerFactory.getDuplicatePropertiesKeysChecker(duplicateExclusions);
        LocalizedMessages messages = duplicateChecker.processCheck(file, lines);
        updateMessages(messages);
    }

    private void updateMessages(LocalizedMessages messages) {
        if (messages != null) {
            for (LocalizedMessage message : messages.getMessages()) {
                getMessageCollector().add(message);
            }
            messages.reset();
        }
    }

    private void checkPhraseApp(File file, List<String> lines) {
        if (enablePhraseApp && phraseAppToken != null) {
            PhraseAppPropertiesKeysChecker phraseAppChecker
                    = checkerFactory.getPhraseAppPropertiesKeysChecker(phraseAppToken);
            LocalizedMessages messages = phraseAppChecker.processCheck(file, lines);
            updateMessages(messages);
        }
    }

    @Override
    protected void setupChild(Configuration aChildConf) throws CheckstyleException {
        super.setupChild(aChildConf);
    }

    public void setDuplicateExclusions(String[] duplicateExclusions) {
        this.duplicateExclusions = duplicateExclusions;
    }

    public void setPhraseAppToken(String phraseAppToken) {
        this.phraseAppToken = phraseAppToken;
    }

    public void setEnablePhraseApp(Boolean enablePhraseApp) {
        this.enablePhraseApp = enablePhraseApp;
    }

    public void setDirectories(String[] directories) {
        this.directories = directories;
    }


    /**
     * Test
     * @param args
     */
    public static void main(String[] args) {
        Matcher matcher = Pattern.compile(PROPERTIES_FILE_REGEX).matcher("");
        System.out.println(matcher.find());

    }
}

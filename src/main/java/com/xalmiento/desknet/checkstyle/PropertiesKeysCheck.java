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

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 03.12.2014 22:25
 *
 * @author Anatoly
 */
public class PropertiesKeysCheck extends AbstractFileSetCheck {

    private String directory;

    private String[] exclusions;

    private String phraseAppToken;

    private Boolean enablePhraseApp;

    private APIServiceFactory apiServiceFactory;

    private CheckerFactory checkerFactory;

    private static final String PROPERTIES_FILE_REGEX = "[^_]*properties$";


    @Override
    protected void processFiltered(File file, List<String> lines) {
        if (checkFileDirectory(file)) {
            initServicesFactories();

            checkDuplicate(file, lines);
            checkPhraseApp(file, lines);
        }
    }

    private void checkDuplicate(File file, List<String> lines) {
        DuplicatePropertiesKeysChecker duplicateChecker
                = checkerFactory.getDuplicatePropertiesKeysChecker(exclusions);
        LocalizedMessages messages = duplicateChecker.processCheck(file, lines);
        updateMessages(messages);
    }

    private void updateMessages(LocalizedMessages messages) {
        if (messages != null) {
            for (LocalizedMessage message : messages.getMessages()) {
                getMessageCollector().add(message);
            }
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

    private boolean checkFileDirectory(File file) {
        String path = file.getParent();
        String fileName = file.getName();
        return path.endsWith(directory) && fileName.matches(PROPERTIES_FILE_REGEX);
    }

    private void initServicesFactories() {
        if (apiServiceFactory == null) {
            apiServiceFactory = new APIServiceFactory();
        }

        if (checkerFactory == null) {
            checkerFactory = new CheckerFactory();
        }
    }

    @Override
    protected void setupChild(Configuration aChildConf) throws CheckstyleException {
        super.setupChild(aChildConf);
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setExclusions(String[] exclusions) {
        this.exclusions = exclusions;
    }

    public void setPhraseAppToken(String phraseAppToken) {
        this.phraseAppToken = phraseAppToken;
    }

    public void setEnablePhraseApp(Boolean enablePhraseApp) {
        this.enablePhraseApp = enablePhraseApp;
    }
}

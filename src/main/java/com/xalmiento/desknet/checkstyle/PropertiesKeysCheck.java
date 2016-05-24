package com.xalmiento.desknet.checkstyle;

import com.puppycrawl.tools.checkstyle.api.*;
import com.xalmiento.desknet.checkstyle.checker.CheckerFactory;
import com.xalmiento.desknet.checkstyle.checker.DuplicatePropertiesKeysChecker;
import com.xalmiento.desknet.checkstyle.checker.PhraseAppPropertiesKeysChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private String projectName;

    private Boolean enablePhraseApp;

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
                    = checkerFactory.getPhraseAppPropertiesKeysChecker(phraseAppToken, projectName);
            LocalizedMessages messages = phraseAppChecker.processCheck(file, lines);
            updateMessages(messages);
        }
    }

    public static void main(String[] args) {
        PropertiesKeysCheck keysCheck = new PropertiesKeysCheck();

        keysCheck.setDirectories(new String[]{"\\"});
        keysCheck.setEnablePhraseApp(true);
        keysCheck.setPhraseAppToken("");
        keysCheck.setProjectName("");
        File file = new File("");
        keysCheck.processFiltered(file, new ArrayList<String>());
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

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setDirectories(String[] directories) {
        this.directories = directories;
    }

}

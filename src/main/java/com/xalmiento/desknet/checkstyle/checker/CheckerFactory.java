package com.xalmiento.desknet.checkstyle.checker;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 17.12.2014 11:26
 *
 * @author a.laguta
 */
public class CheckerFactory {

    private DuplicatePropertiesKeysChecker duplicatePropertiesKeysChecker;

    private PhraseAppPropertiesKeysChecker phraseAppPropertiesKeysChecker;

    public CheckerFactory() {
    }

    public DuplicatePropertiesKeysChecker getDuplicatePropertiesKeysChecker(String[] exclusions) {
        if (duplicatePropertiesKeysChecker == null) {
            duplicatePropertiesKeysChecker = new DuplicatePropertiesKeysChecker(exclusions);
        }
        return duplicatePropertiesKeysChecker;
    }

    public PhraseAppPropertiesKeysChecker getPhraseAppPropertiesKeysChecker(
            String phraseAppToken, String projectName) {
        if (phraseAppPropertiesKeysChecker == null) {
            phraseAppPropertiesKeysChecker = new PhraseAppPropertiesKeysChecker(
                    phraseAppToken, projectName);
        }
        return phraseAppPropertiesKeysChecker;
    }
}

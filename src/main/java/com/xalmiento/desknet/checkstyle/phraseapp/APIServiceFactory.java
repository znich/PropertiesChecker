package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppAPIProjectServiceImpl;
import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppKeysAPIServiceImpl;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Project;

import java.util.List;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 16.12.2014 10:27
 *
 * @author a.laguta
 */
public class APIServiceFactory {

    private PhraseAppKeysAPIService keysAPIService;
    private PhraseAppAPIProjectService projectService;
    private String projectId;
    private String projectName;

    public APIServiceFactory(String projectName) {
        this.projectName = projectName;
    }

    public PhraseAppKeysAPIService getKeysService(String token) {
        initializeProjectId(token);

        if (keysAPIService == null) {
            keysAPIService = new PhraseAppKeysAPIServiceImpl(projectId, token);
        }
        return keysAPIService;
    }

    private void initializeProjectId(String token) {
        if (projectService == null) {
            projectService = new PhraseAppAPIProjectServiceImpl(token);
        }

        if (projectId == null) {
            List<Project> projects = projectService.getProjects();
            for (Project project : projects) {
                if (projectName.equals(project.getName())) {
                    projectId = project.getId();
                    return;
                }
            }
        }
    }
}

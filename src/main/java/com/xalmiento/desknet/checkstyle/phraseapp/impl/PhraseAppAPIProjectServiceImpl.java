package com.xalmiento.desknet.checkstyle.phraseapp.impl;

import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppAPIProjectService;
import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppAPIServiceImplV2;
import com.xalmiento.desknet.checkstyle.phraseapp.model.Project;

import java.util.List;

/**
 * Created by Anatoly on 21.11.2015.
 */
public class PhraseAppAPIProjectServiceImpl extends PhraseAppAPIServiceImplV2<Project>
        implements PhraseAppAPIProjectService {

    public static final String PROJECTS_URL = "projects";

    public PhraseAppAPIProjectServiceImpl(String token) {
        super(token);
    }

    @Override
    public List<Project> getProjects() {
        return getList(PROJECTS_URL, Project.class, null, null);
    }
}

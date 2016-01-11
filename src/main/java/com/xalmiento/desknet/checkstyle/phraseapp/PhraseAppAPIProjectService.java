package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.model.Project;

import java.util.List;

/**
 * Created by Anatoly on 21.11.2015.
 */
public interface PhraseAppAPIProjectService extends PhraseAppAPIService<Project> {

    List<Project> getProjects();
}

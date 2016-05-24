package com.xalmiento.desknet.checkstyle.phraseapp;

import com.xalmiento.desknet.checkstyle.phraseapp.impl.PhraseAppAPIProjectServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xalmiento.desknet.checkstyle.http.HttpRequestUtil.jsonListRequestGet;
import static com.xalmiento.desknet.checkstyle.http.HttpRequestUtil.jsonListRequestPost;


public class PhraseAppAPIServiceImplV2<T> implements PhraseAppAPIService<T> {

    private static final String apiUrl = "https://api.phraseapp.com/api/v2/projects/";
    private static final String ELEMENTS_PER_PAGE = "100";

    public static final String QUERY_PARAM = "q";

    private String token;
    private String projectId;

    public PhraseAppAPIServiceImplV2(String token) {
        this.token = token;
    }

    public PhraseAppAPIServiceImplV2(String token, String projectId) {
        this.projectId = projectId;
        this.token = token;
    }

    @Override
    public List<T> getList(
            String url,
            Class<T> type,
            Map<String, String> parameters, Map<String, String> headers) {

        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        addToken(headers);

        if (parameters == null) {
            parameters = new HashMap<String, String>();
        }
        addElementsPerPage(parameters);

        List<T> result = new ArrayList<T>();

        try {
            result = jsonListRequestGet(createUrl(url), type, parameters, headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<T> postList(
            String url,
            Class<T> type,
            Map<String, String> parameters,
            Map<String, String> headers) {

        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        addToken(headers);

        if (parameters == null) {
            parameters = new HashMap<String, String>();
        }
        addElementsPerPage(parameters);

        List<T> result = new ArrayList<T>();
        try {
            result = jsonListRequestPost(createUrl(url), type, parameters, headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void addToken(Map<String, String> headers) {
        headers.put("Authorization", "token " + token);
    }

    private void addElementsPerPage(Map<String, String> parameters) {
        parameters.put("per_page", ELEMENTS_PER_PAGE);
    }

    private String createUrl(String url) {
        return url.equals(PhraseAppAPIProjectServiceImpl.PROJECTS_URL)
                ? apiUrl : apiUrl + projectId + "/" + url;
    }
}

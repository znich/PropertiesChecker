package com.xalmiento.desknet.checkstyle.phraseapp.impl;

import com.xalmiento.desknet.checkstyle.phraseapp.PhraseAppAPIService;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xalmiento.desknet.checkstyle.http.HttpRequestUtil.*;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 11:01
 *
 * @author a.laguta
 */
public class PhraseAppAPIServiceImpl<T> implements PhraseAppAPIService<T> {

    private String token;

    private Class<T> type;

    private static final String apiUrl = "https://phraseapp.com/api/v1/";

    public PhraseAppAPIServiceImpl(String token) {
        this.token = token;
    }

    @Override
    public List<T> getList(
            String url,
            Class<T> type,
            Map<String, String> parameters,
            Map<String, String> headers) {

        List<T> result = new ArrayList<T>();
        addToken(parameters);
        try {
            result = jsonListRequestGet(createUrl(url), type, parameters, headers);
            return result;
        } catch (IOException e) {
           e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<T> getList(
            String url,
            Class<T> type,
            Map<String, String> parameters,
            Map<String, String[]> arrays,
            Map<String, String> headers) {

        List<T> result = new ArrayList<T>();
        addToken(parameters);
        try {
            result = jsonListRequestGet(createUrl(url), type, parameters, arrays, headers);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String createUrl(String url) {
        return apiUrl + url;
    }

    private void addToken(Map<String, String> parameters) {
        if (parameters == null) {
            parameters = new HashMap<String, String>();
        }
        parameters.put("auth_token", token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

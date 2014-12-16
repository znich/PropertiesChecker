package com.xalmiento.desknet.checkstyle.phraseapp;

import java.util.List;
import java.util.Map;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 10:45
 *
 * @author a.laguta
 */
public interface PhraseAppAPIService<T> {

    List<T> getList(
            String url,
            Class<T> type,
            Map<String, String> parameters,
            Map<String, String> headers);

    List<T> getList(
            String url,
            Class<T> type,
            Map<String, String> parameters,
            Map<String, String[]> arrays,
            Map<String, String> headers);
}

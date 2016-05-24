package com.xalmiento.desknet.checkstyle.http;

import com.xalmiento.desknet.checkstyle.http.parsing.ParsingException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.12.2014 10:13
 *
 * @author a.laguta
 */
public class HttpRequestUtil {

    private static HttpRequestService service = new HttpRequestService();

    public static HttpResponse<String> requestPost(
            String urlAddress,
            Object body,
            Map<String, String> properties) throws IOException {
        try {
            return service.requestPost(urlAddress, body, properties, new StringParser());
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<String> requestGet(
            String urlAddress,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException {
        try {
            return service.requestGet(urlAddress, parameters, properties, new StringParser());
        } catch (ParsingException e) {
            throw new RuntimeException(e);
        }
    }

    //String requests
    public static String stringRequestGet(
            String urlAddress,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException {
        HttpResponse<String> response = requestGet(urlAddress, parameters, properties);
        return stringRequest(response);
    }

    public static String stringRequestPost(
            String urlAddress,
            Object body,
            Map<String, String> properties) throws IOException {
        HttpResponse<String> response = requestPost(urlAddress, body, properties);
        return stringRequest(response);
    }

    private static String stringRequest(
            HttpResponse<String> response) throws IncorrectResponseCodeException {
        if (response.getCode() != 200) {
            throw new IncorrectResponseCodeException(
                    "Response code: " + response.getCode()
                            + "; Response text: " + response.getBody());
        }
        return response.getBody();
    }


    //JSON requests
    public static Map<String, String> jsonMapRequestGet(
            String urlAddress,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException, ParsingException {
        return jsonMapRequestGet(urlAddress, String.class, parameters, properties);
    }

    public static Map<String, String> jsonMapRequestPost(
            String urlAddress,
            Object body,
            Map<String, String> properties) throws IOException, ParsingException {
        return jsonMapRequestPost(urlAddress, String.class, body, properties);
    }

    public static <T> Map<String, T> jsonMapRequestGet(
            String urlAddress,
            Class<T> clazz,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException {
        return checkResponse(service.requestGet(
                urlAddress, parameters, properties, new JsonMapParser<T>(clazz)));
    }

    public static <T> Map<String, T> jsonMapRequestPost(
            String urlAddress,
            Class<T> clazz,
            Object body,
            Map<String, String> properties) throws IOException {
        return checkResponse(
                service.requestPost(urlAddress, body, properties, new JsonMapParser<T>(clazz)));
    }

    public static <T> List<T> jsonListRequestGet(
            String urlAddress,
            Class<T> clazz,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException {

        PaginationHttpResponse<List<T>> response = service.requestGet(
                urlAddress, parameters, properties, new JsonListParser<T>(clazz));

        List<T> result = checkResponse(response);

        while (response.hasNext()) {
            response = service.requestGet(
                    response.getNextLink(), parameters, properties, new JsonListParser<T>(clazz));
            result.addAll(checkResponse(response));
        }
        return result;
    }

    public static <T> List<T> jsonListRequestGet(
            String urlAddress,
            Class<T> clazz,
            Map<String, String> parameters,
            Map<String, String[]> arrays,
            Map<String, String> properties) throws IOException {
        return checkResponse(service.requestGet(
                urlAddress, parameters, properties, new JsonListParser<T>(clazz)));
    }

    public static <T> List<T> jsonListRequestPost(
            String urlAddress,
            Class<T> clazz,
            Object body,
            Map<String, String> properties) throws IOException {

        PaginationHttpResponse<List<T>> response = service.requestPost(
                urlAddress, body, properties, new JsonListParser<T>(clazz));

        List<T> result = checkResponse(response);

        while (response.hasNext()) {
            response = service.requestPost(
                    response.getNextLink(), body, properties, new JsonListParser<T>(clazz));
            result.addAll(checkResponse(response));
        }

        return result;
    }

    public static <T> T jsonRequestGet(
            String urlAddress,
            Class<T> clazz,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException {
        return checkResponse(service.requestGet(
                urlAddress, parameters, properties, new JsonParser<T>(clazz)));
    }

    public static <T> T jsonRequestPost(
            String urlAddress,
            Class<T> clazz,
            Object body,
            Map<String, String> properties) throws IOException {
        return checkResponse(service.requestPost(
                urlAddress, body, properties, new JsonParser<T>(clazz)));
    }

    //XML requests
    public static <T> T xmlRequestGet(
            String urlAddress,
            Class<T> clazz,
            Map<String, String> parameters,
            Map<String, String> properties) throws IOException {
        return checkResponse(service.requestGet(
                urlAddress, parameters, properties, new XmlParser<T>(clazz)));
    }

    public static <T> T xmlRequestPost(
            String urlAddress,
            Class<T> clazz,
            Object body,
            Map<String, String> properties) throws IOException {
        return checkResponse(service.requestPost(
                urlAddress, body, properties, new XmlParser<T>(clazz)));
    }

    private static <T> T checkResponse(HttpResponse<T> response)
            throws IncorrectResponseCodeException {
        if (response.getCode() > 299) {
            throw new IncorrectResponseCodeException(response.getCode());
        }
        return response.getBody();
    }
}

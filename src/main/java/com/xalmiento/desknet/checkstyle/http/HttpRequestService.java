package com.xalmiento.desknet.checkstyle.http;

import com.xalmiento.desknet.checkstyle.http.parsing.Parser;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpRequestService {

    private static final String NEXT_LINK = "next";

    public <T> PaginationHttpResponse<T> requestPost(
            String urlAddress, Object body,
            Map<String, String> headers,
            Parser<T> parser) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlAddress);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (body != null) {
            if (body instanceof HttpEntity) {
                httpPost.setEntity((HttpEntity) body);
            } else if (body instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, String> parameters = (Map<String, String>) body;
                List<NameValuePair> params = new ArrayList<NameValuePair>(parameters.size());
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            } else {
                httpPost.setEntity(new StringEntity(String.valueOf(body)));
            }
        }
        org.apache.http.HttpResponse response = httpClient.execute(httpPost);
        int responseCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        T result = parser.parse(entity.getContent());

        return new PaginationHttpResponse<T>(responseCode, result, getNextLink(response));

    }

    public <T> PaginationHttpResponse<T> requestGet(
            String urlAddress,
            Map<String, String> parameters,
            Map<String, String> headers,
            Parser<T> parser) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();

        URI uri;
        try {
            URIBuilder uriBuilder = new URIBuilder(urlAddress);
            if (parameters != null) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }

        HttpGet httpGet = new HttpGet(uri);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        org.apache.http.HttpResponse response = httpClient.execute(httpGet);
        int responseCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        T result = parser.parse(entity.getContent());

        return new PaginationHttpResponse<T>(responseCode, result, getNextLink(response));
    }

    private String getNextLink(org.apache.http.HttpResponse response) {
        return extractURIByRel(response.getHeaders("link")[0].getValue(), NEXT_LINK);
    }

    private String extractURIByRel(final String linkHeader, final String rel) {
        if (linkHeader == null) {
            return null;
        }

        String uriWithSpecifiedRel = null;
        final String[] links = linkHeader.split(", ");
        String linkRelation;
        for (final String link : links) {
            final int positionOfSeparator = link.indexOf(';');
            linkRelation = link.substring(positionOfSeparator + 1, link.length()).trim();
            if (extractTypeOfRelation(linkRelation).equals(rel)) {
                uriWithSpecifiedRel = link.substring(1, positionOfSeparator - 1);
                break;
            }
        }

        return uriWithSpecifiedRel;
    }

    private Object extractTypeOfRelation(final String linkRelation) {
        return linkRelation.split("=")[1].trim();
    }
}


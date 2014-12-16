package com.xalmiento.desknet.checkstyle.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xalmiento.desknet.checkstyle.http.parsing.Parser;
import com.xalmiento.desknet.checkstyle.http.parsing.ParsingException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


public class HttpRequestService {

    public <T> HttpResponse<T> requestPost(
            String urlAddress, Object body,
            Map<String, String> properties,
            Parser<T> parser) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlAddress);

        if (properties != null){
            for (Map.Entry<String, String> entry:properties.entrySet()){
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (body != null){
            if (body instanceof HttpEntity){
                httpPost.setEntity((HttpEntity)body);
            } else if (body instanceof Map<?, ?>){
                @SuppressWarnings("unchecked")
                Map<String, String> parameters = (Map<String, String>) body;
                List<NameValuePair> params = new ArrayList<NameValuePair>(parameters.size());
                for (Map.Entry<String, String> entry:parameters.entrySet()){
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
        return new HttpResponse<T>(responseCode, result);

    }
    public <T> HttpResponse<T> requestGet(
            String urlAddress,
            Map<String, String> parameters,
            Map<String, String> properties,
            Parser<T> parser) throws IOException{
       return requestGet(urlAddress, parameters, null, properties, parser);
    }


    public <T> HttpResponse<T> requestGet(
            String urlAddress,
            Map<String, String> parameters,
            Map<String, String[]> arrays,
            Map<String, String> properties,
            Parser<T> parser) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();

        URI uri;
        try {
            URIBuilder uriBuilder = new URIBuilder(urlAddress);
            if (parameters != null){
                for (Map.Entry<String, String> entry:parameters.entrySet()){
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            if (arrays != null) {
                for (Map.Entry<String, String[]> entry : arrays.entrySet()) {
                    for (String value : entry.getValue()) {
                        uriBuilder.addParameter(entry.getKey(), value);
                    }
                }
            }
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }

        HttpGet httpGet = new HttpGet(uri);


        if (properties != null){
            for (Map.Entry<String, String> entry:properties.entrySet()){
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        org.apache.http.HttpResponse response = httpClient.execute(httpGet);
        int responseCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        T result = parser.parse(entity.getContent());
        return new HttpResponse<T>(responseCode, result);
    }
}


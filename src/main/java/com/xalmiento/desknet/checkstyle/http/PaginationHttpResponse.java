package com.xalmiento.desknet.checkstyle.http;

/**
 * Created by Anatoly on 23.11.2015.
 */
public class PaginationHttpResponse<T> extends HttpResponse<T> {

    private String nextLink;

    public PaginationHttpResponse(int code, T body, String nextLink) {
        super(code, body);
        this.nextLink = nextLink;
    }

    public String getNextLink() {
        return nextLink;
    }

    public boolean hasNext() {
        return nextLink != null;
    }
}

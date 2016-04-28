package com.cti;

import org.apache.http.HttpStatus;

/**
 * Created by ifeify on 4/28/16.
 */
public class Payload {
    protected int statusCode;
    protected String body;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

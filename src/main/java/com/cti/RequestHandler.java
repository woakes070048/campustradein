package com.cti;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

/**
 * Created by ifeify on 4/28/16.
 */
public abstract class RequestHandler<T> implements Route {
    protected T dto;

    @Override
    public final Object handle(Request request, Response response) {
        PayloadType payloadType = getPayloadType(request);
        Payload payload = doHandle(dto, request.params(), payloadType);
        response.status(payload.getStatusCode());
        response.type(payloadType.toString());
        response.body(payload.getBody());
        return payload.getBody();
    }

    public abstract Payload doHandle(T dto, Map<String, String> queryParams, PayloadType payloadType);

    private PayloadType getPayloadType(Request request) {
        String acceptHeader = request.headers("Accept");
        if(acceptHeader == null) {
            return PayloadType.HTML;
        } else if(acceptHeader.contains("text/html")) {
            return PayloadType.HTML;
        } else if(acceptHeader.contains("application/json")) {
            return PayloadType.JSON;
        } else {
            return PayloadType.XML;
        }
    }

    protected void setSecureCookie(String key, String value) {

    }

    protected void setCookie(String key, String value) {

    }
}

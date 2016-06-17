package com.cti.controller;

import org.apache.http.HttpStatus;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

/**
 * @author ifeify
 */
public class RequiresJsonFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {
        String acceptHeader = request.headers("Accept");
        if(acceptHeader == null || !acceptHeader.contains("application/json")) {
            response.status(HttpStatus.SC_METHOD_NOT_ALLOWED);
            halt();
        }
    }
}

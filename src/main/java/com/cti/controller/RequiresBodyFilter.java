package com.cti.controller;

import org.apache.http.HttpStatus;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

/**
 * @author ifeify
 */
public class RequiresBodyFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {
        if(request.body().isEmpty()) {
            halt(HttpStatus.SC_NOT_ACCEPTABLE, "Body is empty");
        }
    }
}

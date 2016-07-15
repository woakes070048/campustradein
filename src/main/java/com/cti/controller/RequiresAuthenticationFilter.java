package com.cti.controller;

import org.apache.http.HttpStatus;
import spark.Filter;
import spark.Request;
import spark.Response;

import static com.google.common.base.Preconditions.checkNotNull;
import static spark.Spark.halt;

/**
 * @author ifeify
 */
public class RequiresAuthenticationFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {

    }
}

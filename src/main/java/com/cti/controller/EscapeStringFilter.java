package com.cti.controller;


import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.Set;

/**
 * @author ifeify
 */
public class EscapeStringFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {
        Set<String> params = request.queryParams();
        for(String param : params) {

        }
    }
}

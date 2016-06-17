package com.cti.controller;

import com.cti.annotation.Controller;
import spark.Spark;

/**
 * @author ifeify
 */
@Controller
public class RouteConfig {
    public RouteConfig() {
        Spark.before("/users/:username/*", new RequiresLoginFilter());
        Spark.before("/users/:username/books", new RequiresJsonFilter());
        Spark.before("/suggestions/*", new RequiresJsonFilter());
    }
}

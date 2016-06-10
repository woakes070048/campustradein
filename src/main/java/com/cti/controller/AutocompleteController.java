package com.cti.controller;

import com.cti.annotation.Route;
import com.cti.service.BookService;
import com.google.inject.Inject;
import spark.Spark;

/**
 * Created by ifeify on 6/9/16.
 */
public class AutocompleteController extends AbstractController {
    @Inject
    private BookService bookService;

    @Inject
    public AutocompleteController(BookService bookService) {
        this.bookService = bookService;
    }

    @Route
    public void handleISBNSearch() {
        Spark.post("/autocomplete/books/:isbn", (request, response) -> {

            return null;
        }, gson::toJson);
    }

    @Route
    public void handleTitleSearch() {
        
    }
}

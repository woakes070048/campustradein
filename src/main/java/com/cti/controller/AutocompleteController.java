package com.cti.controller;

import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.service.BookService;
import com.google.inject.Inject;
import org.apache.http.HttpStatus;
import spark.Spark;

import java.util.List;

/**
 * @author ifeify
 */
public class AutocompleteController extends AbstractController {
    @Inject
    private BookService bookService;

    @Inject
    public AutocompleteController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Make sure only authenticated users can access this api and only supports JSON
     */
    @Route
    public void applyFilter() {
        Spark.before("/suggestions/*", (request, response) -> {
            String accept = request.headers("Accept");
            if(accept != null && !accept.contains("application/json")) {
                Spark.halt(HttpStatus.SC_UNAUTHORIZED);
                return;
            }
            String sessionID = request.cookie("session");
            // TODO: authenticate user
        });
    }

    @Route
    public void handleISBNSearch() {
        Spark.get("/suggestions/:isbn", (request, response) -> {
            String isbn = request.params("isbn");
            List<Book> books = bookService.findByISBN(isbn);
            return books;
        }, gson::toJson);
    }

    @Route
    public void handleTitleSearch() {
        Spark.get("/suggestions/:title", (request, response) -> {
            String title = request.params("title");
            List<Book> books = bookService.findByTitle(title);
            return books;
        }, gson::toJson);
    }
}

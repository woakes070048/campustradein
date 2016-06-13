package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.model.BookInfo;
import com.cti.service.BooksApi;
import com.google.inject.Inject;
import org.apache.http.HttpStatus;
import spark.Spark;

import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 * The Autocomplete controller is handles request for a book when a user lists a book for sale.
 * The user specifies a title or isbn number of the book and the controller returns a
 * list of books that matches that criteria. The idea is to use the data returned auto-populate
 * the book listing form for the user
 */
@Controller
public class AutocompleteController extends AbstractController {
    @Inject
    private BooksApi booksApi;

    @Inject
    public AutocompleteController(BooksApi booksApi) {
        this.booksApi = booksApi;
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
        Spark.get("/suggestions/isbn/:isbn", (request, response) -> {
            String isbn = request.params("isbn");
            Optional<BookInfo> result = booksApi.findByISBN(isbn);
            if(result.isPresent()) {
                response.status(HttpStatus.SC_OK);
                return result.get();
            } else {
                response.status(HttpStatus.SC_BAD_REQUEST);
                return null;
            }
        }, gson::toJson);
    }

    @Route
    public void handleTitleSearch() {
        Spark.get("/suggestions/title/:title", (request, response) -> {
            String title = request.params("title");
            List<BookInfo> books = booksApi.findByTitle(title);
            return books;
        }, gson::toJson);
    }
}

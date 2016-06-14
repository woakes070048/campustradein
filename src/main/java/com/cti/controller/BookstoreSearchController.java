package com.cti.controller;

import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.repository.Bookstore;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.inject.Inject;
import java.util.List;


/**
 * @author ifeify
 */
public class BookstoreSearchController extends AbstractController {
    private final static Logger logger = LoggerFactory.getLogger(BookstoreSearchController.class);
    private final static int RESULT_SIZE = 20;

    @Inject
    private Bookstore bookstore;

    @Inject
    public BookstoreSearchController(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    @Route
    public void handleISBNSearch() {
        Spark.get("/books/:isbn", (request, response) -> {
            // validate isbn
            String isbn = request.params("isbn");
            int offset = Integer.parseInt(request.queryParams("start"));
            List<Book> books = bookstore.findByISBN13(isbn, offset, RESULT_SIZE);
            response.status(HttpStatus.SC_OK);
            return books;
        }, gson::toJson);
    }

    @Route
    public void handleBookTitleSearch() {
        Spark.get("/books/:title", "application/json", (request, response) -> {
            String title = request.params("title");
            int offset = Integer.parseInt(request.queryParams("start"));
            List<Book> books = bookstore.findByTitle(title, offset, RESULT_SIZE);
            response.status(HttpStatus.SC_OK);
            return books;
        }, gson::toJson);
    }
}

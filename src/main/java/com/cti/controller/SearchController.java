package com.cti.controller;

import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.repository.BookRepository;
import com.cti.service.BookService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.inject.Inject;
import java.util.List;


/**
 * @author ifeify
 */
public class SearchController extends AbstractController {
    private final static Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final static int RESULT_SIZE = 20;

    @Inject
    private BookRepository bookRepository;

    @Inject
    public SearchController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Route
    public void handleISBN13Search() {
        Spark.get("/books/:isbn13", (request, response) -> {
            String isbn13 = request.params("isbn13");
            int offset = Integer.parseInt(request.queryParams("start"));
            List<Book> books = bookRepository.findByISBN13(isbn13, offset, RESULT_SIZE);
            response.status(HttpStatus.SC_OK);
            return books;
        }, gson::toJson);
    }

    @Route
    public void handleISBN10Search() {
        Spark.get("/books/:isbn10", "application/json", (request, response) -> {
            String isbn10 = request.params("isbn10");
            int offset = Integer.parseInt(request.queryParams("start"));
            List<Book> books = bookRepository.findByISBN10(isbn10, offset, RESULT_SIZE);
            response.status(HttpStatus.SC_OK);
            return books;
        }, gson::toJson);
    }

    @Route
    public void handleBookTitleSearch() {
        Spark.get("/books/:title", "application/json", (request, response) -> {
            String title = request.params("title");
            int offset = Integer.parseInt(request.queryParams("start"));
            List<Book> books = bookRepository.findByTitle(title, offset, RESULT_SIZE);
            response.status(HttpStatus.SC_OK);
            return books;
        }, gson::toJson);
    }
}

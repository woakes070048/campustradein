package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.exception.InvalidISBNException;
import com.cti.model.BookInfo;
import com.cti.model.Isbn;
import com.cti.service.BookService;
import com.cti.service.BooksApi;
import com.google.inject.Inject;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
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
public class AutocompleteBookController extends AbstractController {
    @Inject
    private BookService bookService;

    @Inject
    public AutocompleteBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Route
    public void handleISBNSearch() {
        Spark.get("/suggestions/isbn/:isbn", (request, response) -> {
            String isbnNumber = request.params("isbn");
            Isbn isbn = new Isbn(isbnNumber);
            Optional<BookInfo> result = bookService.getBookDetails(isbn);
            if (result.isPresent()) {
                response.status(HttpStatus.SC_OK);
                return result.get();
            } else {
                response.status(HttpStatus.SC_BAD_REQUEST);
                return null;
            }
        }, gson::toJson);
    }
}

package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.repository.Bookstore;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ifeify
 */
@Controller
public class SearchController extends AbstractController {
    private final static Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final static int RESULT_SIZE = 20;

    @Inject
    private Bookstore bookstore;

    @Inject
    public SearchController(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    @Route
    public void handleISBNSearch() {
        Spark.get("/books/:isbn", (request, response) -> {
            // validate isbn
            String isbn = request.params("isbn");
            int offset = Integer.parseInt(request.queryParams("start"));
            List<Book> books = bookstore.findByISBN13(isbn, offset, RESULT_SIZE);

            return books;
        }, gson::toJson);
    }

    @Route
    public void handleBookTitleSearch() {
        Spark.get("/search", (request, response) -> {
            String query = request.queryParams("q");
            String start = request.queryParams("start");
            String pageNumber = request.queryParams("pageNumber");

            int offset;
            if(start == null) {
                offset = 0;
            } else {
                offset = Integer.parseInt(start);
            }

            long totalNumberOfBooks = bookstore.count(query);
            List<Book> books = bookstore.findByTitle(query, offset, RESULT_SIZE);
            Map<String, Object> model = new HashMap<>();
            int numberOfPages = (int)Math.ceil((double)(totalNumberOfBooks / (offset + RESULT_SIZE)));
            model.put("query", query);
            model.put("totalNumberOfBooks", totalNumberOfBooks);
            model.put("books", books);
            model.put("booksPerPage", RESULT_SIZE);
            model.put("currentPage", pageNumber);
            model.put("numberOfPages", numberOfPages);

            return templateEngine.render(new ModelAndView(model, "search.ftl"));
        });
    }
}

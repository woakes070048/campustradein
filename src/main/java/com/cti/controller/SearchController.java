package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.repository.Bookstore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ifeify
 */
@Controller
public class SearchController extends AbstractController {
    private final static Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final static int PAGE_SIZE = 20;

    @Inject
    private Bookstore bookstore;

    @Inject
    public SearchController(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    @Route
    public void browseCatalog() {
        Spark.get("/catalog", (request, response) -> {
            String page = request.queryParams("page");

            long totalNumberOfBooks = bookstore.count();
            int numberOfPages = (int)Math.ceil(totalNumberOfBooks / PAGE_SIZE);
            int pageNumber = page != null ? Integer.parseInt(page) : 1;
            if(pageNumber < 1) {
                pageNumber = 1;
            }
            if(pageNumber > PAGE_SIZE) {
                pageNumber = PAGE_SIZE;
            }
            int offset = (pageNumber - 1) * PAGE_SIZE;
            List<Book> books = bookstore.getRecentListings(offset, PAGE_SIZE);
            Map<String, Object> model = new HashMap<>();
            model.put("totalNumberOfBooks", totalNumberOfBooks);
            model.put("books", books);
            model.put("currentPage", pageNumber);
            model.put("numberOfPages", numberOfPages);
            return templateEngine.render(new ModelAndView(model, "catalog.ftl"));
        });
    }

    @Route
    public void handleItemSearch() {
        Spark.get("/search", (request, response) -> {
            String query = request.queryParams("q");
            String page = request.queryParams("page");

            long totalNumberOfBooks = query != null ? bookstore.count(query) : 0;
            int numberOfPages = (int)Math.ceil(totalNumberOfBooks / PAGE_SIZE);
            int pageNumber = 1;
            if(page != null) {
                pageNumber = Integer.parseInt(page);
                if(pageNumber < 1) {
                    pageNumber = 1;
                }
                if(pageNumber > PAGE_SIZE) {
                    pageNumber = PAGE_SIZE;
                }
            }
            int offset = (pageNumber - 1) * PAGE_SIZE;
            List<Book> books = query != null ? bookstore.findByTitle(query, offset, PAGE_SIZE) : new ArrayList<>();
            Map<String, Object> model = new HashMap<>();

            model.put("query", query);
            model.put("totalNumberOfBooks", totalNumberOfBooks);
            model.put("books", books);
            model.put("currentPage", pageNumber);
            model.put("numberOfPages", numberOfPages);

            return templateEngine.render(new ModelAndView(model, "search.ftl"));
        });
    }
}

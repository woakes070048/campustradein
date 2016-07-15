package com.cti.controller;

import com.cti.common.annotation.Controller;
import com.cti.common.annotation.Route;
import com.cti.model.Book;
import com.cti.service.UserService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpStatus;
import spark.Spark;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author ifeify
 */
@Controller
public class UserController extends AbstractController {
    @Inject
    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Route
    public void addBook() {
        Spark.post("/books", (request, response) -> {
            Book book = gson.fromJson(request.body(), Book.class);
            userService.createNewListing(book);
            return book;
        }, gson::toJson);
    }

    @Route
    public void deleteBook() {
        Spark.delete("/books/:id", (request, response) -> {
            String bookId = StringEscapeUtils.escapeHtml4(request.params(":id"));
            String username = StringEscapeUtils.escapeHtml4(request.params(":user"));
            userService.deleteListing(username, bookId);
            return null;
        });
    }

    @Route
    public void updateBook() {
        Spark.put("/books/:id", (request, response) -> {

            return null;
        });
    }

    @Route
    public void getBook() {
        Spark.get("/books/:id", (request, response) -> {
            String bookId = StringEscapeUtils.escapeHtml4(request.params(":id"));
            String username = StringEscapeUtils.escapeHtml4(request.params(":user"));
            Optional<Book> result = userService.getListing(bookId);
            if(result.isPresent()) {
                response.status(HttpStatus.SC_OK);
                return gson.toJson(result.get());
            } else {
                response.status(HttpStatus.SC_NOT_FOUND);
                return null;
            }
        });
    }
}

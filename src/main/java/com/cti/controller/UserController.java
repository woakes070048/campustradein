package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.service.UserService;
import spark.Spark;

import javax.inject.Inject;

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
        Spark.post("users/:user/books", (request, response) -> {
            Book book = gson.fromJson(request.body(), Book.class);
            userService.createNewListing(book);
            return book;
        }, gson::toJson);
    }

    @Route
    public void deleteBook() {
        Spark.delete("users/:user/books/:id", (request, response) -> {

            return null;
        });
    }

    @Route
    public void updateBook() {
        Spark.put("users/:user/books/:id", (request, response) -> {

            return null;
        });
    }

    @Route
    public void getBook() {
        Spark.get("users/:user/books/:id", (request, response) -> {

            return null;
        });
    }
}

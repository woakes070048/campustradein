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
public class BookListingController extends AbstractController {
    @Inject
    private UserService userService;

    @Inject
    public BookListingController(UserService userService) {
        this.userService = userService;
    }

    @Route
    public void listABook() {
        Spark.post("users/:user/books", (request, response) -> {
            Book book = gson.fromJson(request.body(), Book.class);
            userService.createNewListing(book);
            return book;
        }, gson::toJson);
    }

    @Route
    public void handleImageUpload() {

    }
}

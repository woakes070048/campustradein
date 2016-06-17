package com.cti.controller;

import com.cti.annotation.Controller;
import com.cti.annotation.Route;
import com.cti.model.Book;
import com.cti.service.UserService2;
import com.google.gson.JsonElement;
import org.apache.http.HttpStatus;
import spark.Spark;

import javax.inject.Inject;

/**
 * @author ifeify
 */
@Controller
public class BookListingController extends AbstractController {
    @Inject
    private UserService2 userService2;

    @Inject
    public BookListingController(UserService2 userService2) {
        this.userService2 = userService2;
    }

    @Route
    public void listABook() {
        Spark.post("users/:user/books", (request, response) -> {
            Book book = gson.fromJson(request.body(), Book.class);
            userService2.createNewListing(book);
            return book;
        }, gson::toJson);
    }

    @Route
    public void handleImageUpload() {

    }
}

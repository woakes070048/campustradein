package com.cti.service;

import com.cti.model.Book;
import com.cti.repository.Bookstore;
import com.cti.repository.UserRepository2;

import javax.inject.Inject;


/**
 * @author ifeify
 */
public class UserService2 {
    @Inject
    private UserRepository2 userRepository;
    @Inject
    private Bookstore bookstore;

    @Inject
    public UserService2(UserRepository2 userRepository, Bookstore bookstore) {
        this.userRepository = userRepository;
        this.bookstore = bookstore;
    }

    public void listBook(Book book) {
        bookstore.addBook(book);
        userRepository.addNewListing(book);
    }

    /**
     * When a user sells a book, delete the listing from the product catalog (e.g. book repository)
     * but update its status to sold in the user repository
     * @param book
     */
    public void markSold(Book book) {
        bookstore.deleteBook(book);
        userRepository.updateListing(book);
    }
}

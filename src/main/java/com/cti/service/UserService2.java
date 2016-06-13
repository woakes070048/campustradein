package com.cti.service;

import com.cti.model.Book;
import com.cti.repository.BookRepository;
import com.cti.repository.UserRepository2;

import javax.inject.Inject;


/**
 * @author ifeify
 */
public class UserService2 {
    @Inject
    private UserRepository2 userRepository;
    @Inject
    private BookRepository bookRepository;

    @Inject
    public UserService2(UserRepository2 userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void listBook(Book book) {
        bookRepository.addBook(book);
        userRepository.addNewListing(book);
    }

    /**
     * When a user sells a book, delete the listing from the product catalog (e.g. book repository)
     * but update its status to sold in the user repository
     * @param book
     */
    public void markSold(Book book) {
        bookRepository.deleteBook(book);
        userRepository.updateListing(book);
    }
}

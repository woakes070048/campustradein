package com.cti.repository;

import com.cti.model.Book;

/**
 * @author ifeify
 */
public interface UserRepository2 {
    void addNewListing(Book book);

    void updateListing(Book book);

    void deleteListing(Book book);
}

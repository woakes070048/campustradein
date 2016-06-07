package com.cti.service;

import com.cti.model.Book;

import java.util.List;

/**
 * Created by iolowosusi on 6/7/16.
 */
public class OpenLibraryBookService implements BookService {
    @Override
    public List<Book> findByISBN(String isbn) {
        return null;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }
}

package com.cti.service;

import com.cti.model.Book;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author ifeify
 */
public class GoogleBookService implements BookService {
    @Inject
    @Named("Google Books API Key")
    private String apiKey;

    @Inject
    public GoogleBookService(@Named("Google Books API Key") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Book> findByISBN(String isbn) {
        return null;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }
}

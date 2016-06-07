package com.cti.service;

import com.cti.model.Book;

import java.util.List;

/**
 * Created by iolowosusi on 6/7/16.
 */
public interface BookService {
    List<Book> findByISBN(String isbn);

    List<Book> findByTitle(String title);
}

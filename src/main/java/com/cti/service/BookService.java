package com.cti.service;

import com.cti.model.Book;

import java.io.IOException;
import java.util.List;

/**
 * Created by iolowosusi on 6/7/16.
 */
public interface BookService {
    List<Book> findByISBN(String isbn) throws IOException;

    List<Book> findByTitle(String title);
}

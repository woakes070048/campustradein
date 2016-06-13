package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.Book;
import com.cti.model.BookInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by iolowosusi on 6/7/16.
 */
public interface BookService {
    List<BookInfo> findByISBN(String isbn) throws BooksApiException;

    List<BookInfo> findByTitle(String title) throws BooksApiException;
}

package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.Book;
import com.cti.model.BookInfo;

import java.util.List;

/**
 * Created by iolowosusi on 6/7/16.
 */
public class OpenLibraryBookService implements BookService {
    @Override
    public List<BookInfo> findByISBN(String isbn) throws BooksApiException {
        return null;
    }

    @Override
    public List<BookInfo> findByTitle(String title) throws BooksApiException {
        return null;
    }
}

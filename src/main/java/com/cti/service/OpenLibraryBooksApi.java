package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.BookInfo;

import java.util.List;
import java.util.Optional;

/**
 * Created by iolowosusi on 6/7/16.
 */
public class OpenLibraryBooksApi implements BooksApi {
    @Override
    public Optional<BookInfo> findByISBN(String isbn) throws BooksApiException {
        return null;
    }
}

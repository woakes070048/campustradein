package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.BookInfo;

import java.util.List;
import java.util.Optional;

/**
 * Created by iolowosusi on 6/7/16.
 */
public interface BooksApi {
    Optional<BookInfo> findByISBN(String isbn) throws BooksApiException;

    List<BookInfo> findByTitle(String title) throws BooksApiException;
}

package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.BookInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public interface BooksApi {
    Optional<BookInfo> findByISBN(String isbn) throws BooksApiException;
}

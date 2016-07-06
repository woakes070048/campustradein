package com.cti.service;

import com.cti.common.exception.BooksApiException;
import com.cti.model.BookInfo;

import java.util.Optional;

/**
 * @author ifeify
 */
public interface BooksApi {
    Optional<BookInfo> findByISBN(String isbn) throws BooksApiException;
}

package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.BookInfo;

import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author ifeify
 * The book service class queries an external books api (e.g Google Books api, Open Library api)
 * to get detailed information for a book. It uses a cache with a maximum size of 10,000 internally to
 * cache results and eliminates the need to call the api for the same query. It's current limitation is
 * that there is a chance for duplicates. If a user queries for the same book but provides the ISBN13 number and ISBN10
 * number, it's not recognized as the same book and so two entries for the book will be created in the cache
 */
@Singleton
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private LoadingCache<String, Optional<BookInfo>> cache;

    @Inject
    private BooksApi booksApi;

    @Inject
    public BookService(BooksApi booksApi) {
        this.booksApi = booksApi;
        cache = CacheBuilder.newBuilder()
                            .maximumSize(10000)
                            .recordStats()
                            .build(new CacheLoader<String, Optional<BookInfo>>() {
                                @Override
                                public Optional<BookInfo> load(String key) throws Exception {
                                    return booksApi.findByISBN(key);
                                }
                            });
    }

    public Optional<BookInfo> getBookDetails(String isbn) throws BooksApiException {
        try {
            Optional<BookInfo> result = cache.get(isbn);
            CacheStats cacheStats = cache.stats();
            logger.info("Cache hit(s): {}", cacheStats.hitCount());
            logger.info("Cache miss(es): {}", cacheStats.missCount());
            logger.info("Cache load count: {}", cacheStats.loadCount());
            return result;
        } catch (ExecutionException e) {
            throw new BooksApiException(e);
        }
    }
}

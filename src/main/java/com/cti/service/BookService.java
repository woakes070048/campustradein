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
 */
@Singleton
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookService.class);
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
                                    BookInfo bookInfo = booksApi.findByISBN(key);
                                    return Optional.ofNullable(bookInfo);
                                }
                            });
    }

    public Optional<BookInfo> getBookDetails(String isbn) throws BooksApiException {
        try {
            Optional<BookInfo> result = cache.get(isbn);
            CacheStats cacheStats = cache.stats();
            logger.info(cacheStats.toString());
            return result;
        } catch (ExecutionException e) {
            throw new BooksApiException(e);
        }
    }


}

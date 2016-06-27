package com.cti.service;

import com.cti.model.BookInfo;
import com.cti.model.Isbn;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author ifeify
 * The book service class queries an external books api (e.g Google Books api, Open Library api)
 * to get detailed information for a book. It uses a cache with a maximum size of 10,000 internally to
 * cache results and eliminates the need to call the api for the same query.
 */
@Singleton
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private LoadingCache<Isbn, Optional<BookInfo>> cache;

    @Inject
    private BooksApi booksApi;

    @Inject
    public BookService(BooksApi booksApi) {
        this.booksApi = booksApi;
        cache = CacheBuilder.newBuilder()
                            .maximumSize(10000)
                            .recordStats()
                            .build(new CacheLoader<Isbn, Optional<BookInfo>>() {
                                @Override
                                public Optional<BookInfo> load(Isbn isbn) throws Exception {
                                    return booksApi.findByISBN(isbn.toIsbn13());
                                }
                            });
    }

    public Optional<BookInfo> getBookDetails(Isbn isbn) {
        try {
            Optional<BookInfo> result = cache.get(isbn);
            CacheStats cacheStats = cache.stats();
            logger.info("Cache hit(s): {}", cacheStats.hitCount());
            logger.info("Cache miss(es): {}", cacheStats.missCount());
            logger.info("Cache load count: {}", cacheStats.loadCount());
            return result;
        } catch (ExecutionException e) {
            return Optional.empty();
        }
    }
}

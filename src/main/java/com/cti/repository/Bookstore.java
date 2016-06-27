package com.cti.repository;

import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * The product catalog is essentially the book store or book repository.
 * By default, all methods return items that are sorted based on the date it was listed,
 * starting with the most recent. Appropriate methods also have pagination support
 * @author ifeify
 */
public interface Bookstore {
    /**
     * @param title title of the book
     * @param start offset from start records
     * @param size the number of books to return
     * @return a list of books that match {@code title} sorted in descending order by date
     */
    List<Book> findByTitle(String title, int start, int size);

    /**
     * @param isbn13 ISBN 13 number of the book
     * @param start
     * @param size
     * @return a list of books that match {@code isbn13} sorted in descending order by date
     */
    List<Book> findByISBN13(String isbn13, int start, int size);

    /**
     * @param isbn11 ISBN 131 number of the book
     * @param start
     * @param size
     * @return a list of books that match {@code isbn13} sorted in descending order by date
     */
    List<Book> findByISBN10(String isbn11, int start, int size);

    /**
     * Queries the datastore using the book's unique identifier
     * @param uuid unique identifier for the book
     * @return the book if it exists in the datastore
     */
    Optional<Book> findById(String uuid);

    /**
     * Adds a new book to the datastore
     * @param book
     * @return returns a unique identifier that can be used to query the datastore for this particular book
     */
    void addBook(Book book);

    void deleteBook(String bookId);

    void deleteBooksListedBy(String username) throws UserNotFoundException;
}

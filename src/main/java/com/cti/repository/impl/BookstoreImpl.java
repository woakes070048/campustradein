package com.cti.repository.impl;

import com.cti.common.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.repository.Bookstore;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

/**
 * @author ifeify
 * There are three indexes we create for fast retrieval. We index on isbn numbers, full-text index
 * on book title, index on price, and index on date in descending order
 */
public class BookstoreImpl implements Bookstore {
    private MongoDatabase bookstore;
    private MongoCollection<Document> books;

    @Inject
    public BookstoreImpl(MongoClient mongoClient) {
        this.bookstore = mongoClient.getDatabase("campustradein");
        this.books = bookstore.getCollection("books");
        books.createIndex(Indexes.ascending("bookId"));
        books.createIndex(Indexes.compoundIndex(Indexes.descending("dateListedOn"),
                                                Indexes.descending("price")));

        books.createIndex(Indexes.compoundIndex(Indexes.descending("dateListedOn"),
                                                Indexes.descending("price"),
                                                Indexes.text("title")));
    }

    @Override
    public List<Book> findByTitle(String title, int start, int size) {
        List<Document> documents = new ArrayList<>();
        books.find(eq("title", title)).sort(descending("dateListedOn"))
                                        .skip(start)
                                        .limit(size)
                                        .into(documents);

        List<Book> searchResult = new ArrayList<>();
        mapToDomainModel(documents, searchResult);
        return searchResult;
    }

    @Override
    public List<Book> findByISBN13(String isbn13, int start, int size) {
        List<Document> documents = new ArrayList<>();
        books.find(eq("isbn13", isbn13)).sort(descending("dateListedOn"))
                                        .skip(start)
                                        .limit(size)
                                        .into(documents);

        List<Book> searchResult = new ArrayList<>();
        mapToDomainModel(documents, searchResult);
        return searchResult;
    }

    @Override
    public List<Book> findByISBN10(String isbn10, int start, int size) {
        List<Document> documents = new ArrayList<>();
        books.find(eq("isbn10", isbn10)).sort(descending("dateListedOn"))
                                        .skip(start)
                                        .limit(size)
                                        .into(documents);

        List<Book> searchResult = new ArrayList<>();
        mapToDomainModel(documents, searchResult);
        return searchResult;
    }

    @Override
    public Optional<Book> findById(String bookId) {
        Document document = books.find(Filters.eq("bookId", bookId)).first();
        if(document == null) {
            return Optional.empty();
        }
        Book book = new Book();
        mapToDomainModel(document, book);
        return Optional.of(book);
    }

    @Override
    public void addBook(Book book) {
        Document document = new Document("bookId", book.getBookId())
                                .append("title", book.getTitle())
                                .append("authors", book.getAuthors())
                                .append("isbn13", book.getIsbn13())
                                .append("isbn10", book.getIsbn10())
                                .append("listedBy", book.getListedBy())
                                .append("dateListedOn", book.getDateListed())
                                .append("price", book.getPrice())
                                .append("condition", book.getCondition())
                                .append("tags", book.getCategories());
        books.insertOne(document);
    }

    @Override
    public List<Book> getRecentListings(int start, int size) {
        List<Document> documents = new ArrayList<>();
        books.find().sort(descending("dateListedOn"))
                    .skip(start)
                    .limit(size)
                    .into(documents);
        List<Book> results = new ArrayList<>();
        mapToDomainModel(documents, results);
        return results;
    }

    @Override
    public long count() {
        return books.count();
    }

    @Override
    public long count(String title) {
        return books.count(Filters.eq("title", title));
    }

    @Override
    public void deleteBook(String bookId)  {
        DeleteResult deleteResult = books.deleteOne(Filters.eq("bookId", bookId));
        if(deleteResult.getDeletedCount() == 0) {
            throw new IllegalArgumentException(bookId + " is not a valid book identification number");
        }
    }

    @Override
    public void deleteBooksListedBy(String username) throws UserNotFoundException {
        DeleteResult deleteResult = books.deleteMany(Filters.eq("listedBy", username));
        if(deleteResult.getDeletedCount() == 0) {
            throw new UserNotFoundException(username + " is not a valid user");
        }
    }

    private void mapToDomainModel(List<Document> documents, List<Book> bookList) {
        for(Document document : documents) {
            if(document != null) {
                Book book = new Book();
                book.setBookId(document.getString("bookId"));
                book.setTitle(document.getString("title"));
                book.setAuthors((List<String>)document.get("authors"));
                book.setIsbn13(document.getString("isbn13"));
                book.setIsbn10(document.getString("isbn10"));
                book.setListedBy(document.getString("listedBy"));
                book.setDateListed(document.getDate("dateListedOn"));
                book.setPrice(document.getDouble("price"));
                book.setCondition(document.getString("condition"));
                book.setCategories((List<String>)document.get("tags"));
                bookList.add(book);
            }
        }
    }

    private void mapToDomainModel(Document document, Book book) {
        if(document != null) {
            book.setBookId(document.getString("bookId"));
            book.setTitle(document.getString("title"));
            book.setAuthors((List<String>)document.get("authors"));
            book.setIsbn13(document.getString("isbn13"));
            book.setIsbn10(document.getString("isbn10"));
            book.setListedBy(document.getString("listedBy"));
            book.setDateListed(document.getDate("dateListedOn"));
            book.setPrice(document.getDouble("price"));
            book.setCondition(document.getString("condition"));
            book.setCategories((List<String>)document.get("tags"));
        }
    }
}

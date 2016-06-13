package com.cti.service;

import com.cti.exception.BooksApiException;
import com.cti.model.BookInfo;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ifeify
 */
public class GoogleBooksApi implements BooksApi {
    private static final Logger logger = LoggerFactory.getLogger(GoogleBooksApi.class);

    private static final String APPLICATION_NAME = "campustradein";
    private static final int RESULT_SIZE = 5; // only want to return at most 5 results

    @Inject
    @Named("Google Books API Key")
    private String apiKey;

    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private final Books books;

    @Inject
    public GoogleBooksApi(@Named("Google Books API Key") String apiKey) throws GeneralSecurityException, IOException {
        this.apiKey = apiKey;
        books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                                        .setApplicationName(APPLICATION_NAME)
                                        .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                                        .build();
    }

    private List<BookInfo> find(String query) throws BooksApiException {
        List<BookInfo> list = new ArrayList<>();
        try {
            Books.Volumes.List volumesList = books.volumes().list(query);
            volumesList.setMaxResults((long) RESULT_SIZE);
            volumesList.setPrettyPrint(true);
            // return a partial response rather than a full response. performance boost
            // TODO: not working at the moment
//            volumesList.setFields("items(title,authors,publisher,publishedDate,industryIdentifiers,categories,infoLink)");

            // execute the query
            Volumes volumes = volumesList.execute();
            if (volumes.getTotalItems() != 0 && volumes.getItems() != null) {
                for (Volume volume : volumes.getItems()) {
                    Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
                    BookInfo bookInfo = new BookInfo();
                    bookInfo.setTitle(volumeInfo.getTitle());
                    bookInfo.setAuthors(volumeInfo.getAuthors());
                    bookInfo.setPreviewLink(volumeInfo.getInfoLink());
                    bookInfo.setPublisher(volumeInfo.getPublisher());
                    bookInfo.setPublishedDate(volumeInfo.getPublishedDate());
                    bookInfo.setTags(volumeInfo.getCategories());
                    for (Volume.VolumeInfo.IndustryIdentifiers identifier : volumeInfo.getIndustryIdentifiers()) {
                        if (identifier.getType().equals("ISBN_13")) {
                            bookInfo.setIsbn13(identifier.getIdentifier());
                        } else if (identifier.getType().equals("ISBN_10")) {
                            bookInfo.setIsbn10(identifier.getIdentifier());
                        }
                    }
                    list.add(bookInfo);
                    logger.info(bookInfo.toString());
                }
            }
        } catch(Exception e) {
            throw new BooksApiException(e);
        }
        return list;
    }

    @Override
    public BookInfo findByISBN(String isbn) throws BooksApiException {
        String query = "isbn:" + isbn;
        return find(query).get(0); // should only return one result
    }

    @Override
    public List<BookInfo> findByTitle(String title) throws BooksApiException {
        String query = "title:" + title;
        return find(query);
    }
}

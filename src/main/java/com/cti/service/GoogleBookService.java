package com.cti.service;

import com.cti.model.Book;
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
public class GoogleBookService implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(GoogleBookService.class);

    private static final String APPLICATION_NAME = "campustradein";
    private static final int RESULT_SIZE = 5; // only want to return at most 5 results

    @Inject
    @Named("Google Books API Key")
    private String apiKey;

    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private final Books books;

    @Inject
    public GoogleBookService(@Named("Google Books API Key") String apiKey) throws GeneralSecurityException, IOException {
        this.apiKey = apiKey;
        books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                                        .setApplicationName(APPLICATION_NAME)
                                        .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                                        .build();
    }

    @Override
    public List<Book> findByISBN(String isbn) throws IOException {
        List<Book> list = new ArrayList<>();
        String query = "isbn:" + isbn;
        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setMaxResults((long)RESULT_SIZE);
        volumesList.setPrettyPrint(true);

        volumesList.setProjection("lite");
//        volumesList.setFields("title,authors,"); query for the FIELDS we really need

        // execute the query
        Volumes volumes = volumesList.execute();
        if(volumes.getTotalItems() != 0 && volumes.getItems() != null) {
            for(Volume volume : volumes.getItems()) {
                Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
                Book book = new Book();
                book.setTitle(volumeInfo.getTitle());
                for(Volume.VolumeInfo.IndustryIdentifiers identifier: volumeInfo.getIndustryIdentifiers()) {
                    if(identifier.getType().equals("ISBN_13")) {
                        book.setIsbn13(identifier.getIdentifier());
                    } else if(identifier.getType().equals("ISBN_10")) {
                        book.setIsbn10(identifier.getIdentifier());
                    }
                }
                list.add(book);
                logger.info(volumeInfo.toPrettyString());
            }
        }
        return list;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }
}

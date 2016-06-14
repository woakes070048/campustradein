import com.cti.config.ApplicationConfig;
import com.cti.exception.BooksApiException;
import com.cti.model.BookInfo;
import com.cti.service.BooksApi;
import com.google.inject.Guice;
import com.google.inject.Injector;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public class GoogleBooksApiTest {
    private BooksApi booksApi;

    public GoogleBooksApiTest() {
        Injector injector = Guice.createInjector(new ApplicationConfig());
        booksApi = injector.getInstance(BooksApi.class);
    }

    @Test
    public void testISBN13Search() throws BooksApiException {
        String isbn13 = "9780321468932";
        String bookTitle = "Absolute C++";
        String author = "Walter J. Savitch";

        Optional<BookInfo> result = booksApi.findByISBN(isbn13);
        if(result.isPresent()) {
            BookInfo book = result.get();
            assertTrue(book.getTitle().contains(bookTitle));
            Optional<String> authors = book.getAuthors().stream()
                                                .filter(str -> str.equalsIgnoreCase(author))
                                                .findFirst();
            if(result.isPresent()) {
                String authorName = authors.get();
                assertTrue(authorName.equalsIgnoreCase(author));
            } else {
                fail("No author found for " + bookTitle);
            }
        } else {
            fail("Google could not find a book with isbn13 " + isbn13);
        }
    }
}

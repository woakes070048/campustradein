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
 * Created by iolowosusi on 6/10/16.
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

        List<BookInfo> books = booksApi.findByISBN(isbn13);
        assertNotEquals(0, books.size());
        BookInfo book = books.get(0);
        assertTrue(book.getTitle().contains(bookTitle));
        Optional<String> result = book.getAuthors().stream()
                                                    .filter(str -> str.equalsIgnoreCase(author))
                                                    .findFirst();
        if(result.isPresent()) {
            String authorName = result.get();
            assertTrue(authorName.equalsIgnoreCase(author));
        } else {
            fail();
        }
    }
}

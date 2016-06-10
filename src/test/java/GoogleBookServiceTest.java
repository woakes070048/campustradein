import com.cti.config.ApplicationModule;
import com.cti.model.Book;
import com.cti.service.BookService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by iolowosusi on 6/10/16.
 */
public class GoogleBookServiceTest {
    private BookService bookService;

    public GoogleBookServiceTest() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        bookService = injector.getInstance(BookService.class);
    }

    @Test
    public void testISBN13SearchWithNoDashes() throws IOException {
        String isbn13 = "9780321468932";
        String bookTitle = "Absolute C++";
        String author = "Walter Savitch";

        List<Book> books = bookService.findByISBN(isbn13);
        assertNotEquals(0, books.size());
        assertTrue("Only 5 items should be returned by Google Books API", books.size() <= 5);

        Book book = books.get(0);
        assertTrue(book.getTitle().contains(bookTitle));
    }
}

import com.cti.config.ApplicationConfig;
import com.cti.model.Book;
import com.cti.repository.Bookstore;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author ifeify
 */
public class BookstoreTest {
    private MongoClient mongoClient;
    private Bookstore bookStore;
    private Book book = new Book();

    public BookstoreTest() {
        Injector injector = Guice.createInjector(new ApplicationConfig());
        mongoClient = injector.getInstance(MongoClient.class);
        bookStore = injector.getInstance(Bookstore.class);
    }

    @Before
    public void setUp() {
        book.setTitle("Absolute C++");
        book.addAuthor("Walter J. Savitch");
        book.setIsbn10("0321468937");
        book.setIsbn13("9780321468932");
        book.addCategory("Computer Science");
        book.setCondition("Good condition");
        book.setDateListed(new Date());
        book.setListedBy("ifeify");
        book.setPrice(56.99);
        bookStore.addBook(book);
        System.out.println("Book to be added:");
        System.out.println(book);
    }

    @Test
    public void testFindById() {
        Optional<Book> result = bookStore.findById(book.getBookId());
        if(result.isPresent()) {
            Book returnedBook = result.get();
            System.out.println("Returned book:");
            System.out.println(returnedBook);
            assertTrue(returnedBook.equals(book));
        } else {
            fail();
        }
    }

    @After
    public void cleanUp() {
//        mongoClient.getDatabase("campustradein").drop();
    }
}


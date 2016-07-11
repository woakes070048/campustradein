import com.cti.common.exception.EncryptionException;
import com.cti.common.exception.UserAlreadyExistsException;
import com.cti.common.exception.UserNotFoundException;
import com.cti.config.AppConfig;
import com.cti.model.Book;
import com.cti.repository.Bookstore;
import com.cti.service.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * @author ifeify
 */
public class SearchTest {
    private static Bookstore bookstore;
    private static MongoClient mongoClient;
    private static UserService userService;
    private static final String buyer = "james";
    private static final String seller = "michelle";


    @BeforeClass
    public static void setup() throws UserAlreadyExistsException, EncryptionException, UserNotFoundException, InterruptedException {
        Injector injector = Guice.createInjector(new AppConfig());
        bookstore = injector.getInstance(Bookstore.class);
        mongoClient = injector.getInstance(MongoClient.class);
        userService = injector.getInstance(UserService.class);

        // register some users
        userService.createNewUser(TestUtil.mockUser(buyer));
        userService.createNewUser(TestUtil.mockUser(seller));

        Book cppBook = TestUtil.newCppBookListedBy(seller);
        TimeUnit.SECONDS.sleep(2); // wait for X seconds before creating another book
        Book javaBook = TestUtil.newJavaBookListedBy(buyer);
        userService.createNewListing(cppBook);
        userService.createNewListing(javaBook);
    }

    @Test
    public void fullTextSearch() {
        List<Book> books = bookstore.findByTitle("absolute", 0, 20);
        books.forEach(System.out::println);
        assertTrue(books.size() == 2);
    }

    @Test
    public void exactMatchTest() {
        List<Book> books = bookstore.findByTitle("Absolute C++", 0, 20);
        books.forEach(System.out::println);
        assertTrue(books.size() >= 1);
    }

    @AfterClass
    public static void tearDown() {
        mongoClient.dropDatabase("campustradein");
    }
}

import com.cti.config.AppConfig;
import com.cti.exception.EncryptionException;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;
import com.cti.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ifeify
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FunctionalTest {
    private static final Logger logger = LoggerFactory.getLogger(FunctionalTest.class);
    private Gson gson = new GsonBuilder()
                                .setPrettyPrinting()
                                .create();
    private UserService userService = Guice.createInjector(new AppConfig()).getInstance(UserService.class);
    private UserAccount userAccount;

    @Before
    public void registerUser() throws UserAlreadyExistsException, EncryptionException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", "falz");
        jsonObject.addProperty("email", "ifeify92@gmail.com");
        jsonObject.addProperty("password", "badhguys");
        jsonObject.addProperty("college", "Wichita State University");
        userAccount = gson.fromJson(jsonObject, UserAccount.class);
        userService.createNewUser(userAccount);
    }

    @Test
    public void addABook() throws UserNotFoundException {
        Book book = new Book();
        book.setPrice(99.99);
        book.setTitle("Absolute Java");
        book.setIsbn13("9780136083825");
        book.setIsbn10("013608382X");
        book.setListedBy(userAccount.getUsername());
        book.addCategory("Computer Science");
        book.addCategory("Java");
        book.setCondition("Fairly new");
        book.addAuthor("Walter J. Savitch");

        userService.createNewListing(book);
        logger.info(book.toString());
    }

    @After
    public void deleteUser() throws UserNotFoundException {
        userService.deleteUser(userAccount.getUsername());
    }
}

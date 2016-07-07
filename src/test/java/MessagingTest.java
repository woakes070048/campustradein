import com.cti.App;
import com.cti.config.AppConfig;
import com.cti.common.exception.EncryptionException;
import com.cti.common.exception.UserAlreadyExistsException;
import com.cti.common.exception.UserNotFoundException;
import com.cti.messenger.Message;
import com.cti.messenger.MessagingService;
import com.cti.model.Book;
import com.cti.model.UserAccount;
import com.cti.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ifeify
 */
public class MessagingTest {
    private static final Logger logger = LoggerFactory.getLogger(MessagingTest.class);
    private UserService userService;
    private MessagingService messagingService;
    private MongoClient mongoClient;

    private static final String buyer = "james";
    private static final String seller = "michelle";

    public MessagingTest() {
        Injector injector = Guice.createInjector(new AppConfig());
        userService = injector.getInstance(UserService.class);
        messagingService = injector.getInstance(MessagingService.class);
        mongoClient = injector.getInstance(MongoClient.class);
        mongoClient.dropDatabase(AppConfig.DATABASE);
    }

    @Before
    public void registerUsersAndListABook() throws UserAlreadyExistsException, EncryptionException, UserNotFoundException {
        userService.createNewUser(TestUtil.mockUser(buyer));
        userService.createNewUser(TestUtil.mockUser(seller));

        Book book = TestUtil.newBookListedBy(seller);
        userService.createNewListing(book);
    }

    @Test
    public void inquireAboutABook() throws UserNotFoundException {
        Message sentMessage = new Message();
        sentMessage.setSender(buyer);
        sentMessage.setReceipient(seller);
        sentMessage.setSubject("Regarding book: Absolute C++");
        sentMessage.setBody("I want to buy it. Would you accept $320");

        assertEquals("There should be no messages for " + seller, 0, messagingService.getInbox(seller).size());
        messagingService.send(sentMessage);

        System.out.println("Message sent: " + sentMessage);

        List<Message> messages = messagingService.getInbox(seller);
        assertTrue(seller + " should have 1 message", messages.size() == 1);
        Message receivedMessage = messages.get(0);

        System.out.println("Message received: " + receivedMessage);
        assertTrue(receivedMessage.getSender().equals(sentMessage.getSender()));
        assertTrue(receivedMessage.getConversationId().equals(sentMessage.getConversationId()));
    }

    @After
    public void deleteUser() throws UserNotFoundException {
        //mongoClient.dropDatabase(AppConfig.DATABASE);
    }
}

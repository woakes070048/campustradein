import com.cti.model.Book;
import com.cti.model.Isbn;
import com.cti.model.UserAccount;

/**
 * @author ifeify
 */
public class TestUtil {
    static UserAccount mockUser(String name, String email, String password) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(name);
        userAccount.setEmail(email);
        userAccount.setPassword(password);
        return userAccount;
    }

    static UserAccount mockUser(String name) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(name);
        userAccount.setEmail("campustradein@gmail.com");
        userAccount.setPassword("averybadpassword");
        return userAccount;
    }

    static Book newCppBookListedBy(String username) {
        Book book = new Book();
        book.setTitle("Absolute C++");
        book.addAuthor("Walter J. Savitch");
        book.setIsbn13("9780321468932");
        book.setIsbn10("0321468937");
        book.addCategory("Computer Science");
        book.addCategory("C++");
        book.addCategory("Programming");
        book.setCondition("Fairly new");
        book.setPrice(99.25);
        book.setListedBy(username);
        return book;
    }

    static Book newJavaBookListedBy(String username) {
        Book book = new Book();
        book.setTitle("Absolute Java");
        book.addAuthor("Walter J. Savitch");
        book.setIsbn13("9780136083825");
        book.setIsbn10("013608382X");
        book.addCategory("Computer Science");
        book.addCategory("Java");
        book.addCategory("Programming");
        book.setCondition("Had it for 3 years");
        book.setPrice(40);
        book.setListedBy(username);
        return book;
    }
}

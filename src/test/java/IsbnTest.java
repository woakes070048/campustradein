import com.cti.exception.InvalidISBNException;
import com.cti.model.Isbn;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author ifeify
 */
public class IsbnTest {
    @Test
    public void testValidISBN13() throws InvalidISBNException {
        String correctISBN13 = "978-0-321-46893-2";
        String correctISBN10 = "0321468937";

        Isbn isbn = new Isbn(correctISBN13);
        System.out.println(isbn);
        assertTrue(isbn.toIsbn10().equals(correctISBN10));
    }

    @Test
    public void testValidISBN10() throws InvalidISBNException {
        String correctISBN13 = "9780321468932";
        String correctISBN10 = "0-321-46893-7";

        Isbn isbn = new Isbn(correctISBN10);
        System.out.println(isbn);
        assertTrue(isbn.toIsbn13().equals(correctISBN13));
    }

    @Test
    public void testIsbn13AndIsbn10AreSame() throws InvalidISBNException {
        Isbn isbn13 = new Isbn("978-0-321-46893-2");
        Isbn isbn10 = new Isbn("0-321-46893-7");
        assertTrue(isbn13.equals(isbn10));
    }
}

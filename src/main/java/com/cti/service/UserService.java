package com.cti.service;

import com.cti.auth.EncrypterFactory;
import com.cti.auth.Password;
import com.cti.exception.EncryptionException;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;
import com.cti.repository.Bookstore;
import com.cti.repository.UserRepository;
import com.cti.smtp.Email;
import com.cti.smtp.Mailer;
import com.cti.smtp.SMTPMailException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;


/**
 * @author ifeify
 */
@Singleton
public class UserService {
    @Inject
    private Bookstore bookstore;

    @Inject
    private Mailer mailer;

    @Inject
    private UserRepository userRepository;

    public void createNewListing(Book book) throws UserNotFoundException {
        Optional<UserAccount> result = userRepository.findByUsername(book.getListedBy());
        if(!result.isPresent()) {
            throw new UserNotFoundException("username " + book.getListedBy() + " does not exist");
        }
        // TODO: should be a unit of work
        userRepository.addBookListing(book);
        bookstore.addBook(book);
    }

    public boolean isEmailRegistered(String email) {
        Optional<UserAccount> result = userRepository.findByEmail(email);
        if(result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUsernameRegistered(String username) {
        Optional<UserAccount> result = userRepository.findByUsername(username);
        return result.isPresent();
    }

    public void createNewUser(UserAccount userAccount) throws UserAlreadyExistsException, EncryptionException {
        // TODO: validate user
        Password hashedPassword = new Password.Builder()
                                            .plainTextPassword(userAccount.getPassword())
                                            .useEncrypter(EncrypterFactory.getEncrypter("PBKDF2WithHmacSHA1"))
                                            .hash();
        userAccount.setPassword(hashedPassword.toString());
        userRepository.addUser(userAccount);
    }

    public void sendNotification(Email email) throws SMTPMailException {
        mailer.mail(email);
    }
}

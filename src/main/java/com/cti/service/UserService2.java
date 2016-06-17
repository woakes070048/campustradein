package com.cti.service;

import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;
import com.cti.repository.Bookstore;
import com.cti.repository.UserRepository2;
import org.eclipse.jetty.server.Authentication;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;


/**
 * @author ifeify
 */
@Singleton
public class UserService2 {
    @Inject
    private Bookstore bookstore;

    @Inject
    private UserRepository2 userRepository2;

    public void createNewListing(Book book) throws UserNotFoundException {
        Optional<UserAccount> result = userRepository2.findByUsername(book.getListedBy());
        if(!result.isPresent()) {
            throw new UserNotFoundException("book listed by " + book.getListedBy() + " does not exist");
        }
        // TODO: should be a unit of work
        userRepository2.addBookListing(book);
        bookstore.addBook(book);
    }

    public boolean isEmailRegistered(String email) {
        Optional<UserAccount> result = userRepository2.findByEmail(email);
        if(result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUsernameRegistered(String username) {
        Optional<UserAccount> result = userRepository2.findByUsername(username);
        if(result.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}

package com.cti.repository;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;

/**
 * @author ifeify
 */
public interface UserRepository2 {
    void addUser(UserAccount userAccount) throws UserAlreadyExistsException;

    void activateUser(String username) throws UserNotFoundException;

    UserAccount findByUsername(String username) throws UserNotFoundException;

    UserAccount findByEmail(String email) throws UserNotFoundException;

    boolean isEmailRegistered(String email);

    boolean isUsernameRegistered(String username);

    void deleteUser(String username) throws UserNotFoundException;

    void addNewListing(Book book);

    void updateListing(Book book);

    void deleteListing(Book book);
}

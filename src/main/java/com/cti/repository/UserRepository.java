package com.cti.repository;

import com.cti.common.auth.Credential;
import com.cti.common.exception.UserAlreadyExistsException;
import com.cti.common.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;

import java.util.Optional;

/**
 * @author ifeify
 */
public interface UserRepository {
    Optional<Credential> getUserCredential(String usernameOrEmail);

    void addUser(UserAccount userAccount) throws UserAlreadyExistsException;

    void activateUser(String username) throws UserNotFoundException;

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByEmail(String email);

    void deleteUser(String username) throws UserNotFoundException;

    void addBookListing(Book book);

    void updateListing(Book book);

    void deleteListing(String username, String bookId);
}

package com.cti.repository.impl;

import com.cti.auth.Credential;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;
import com.cti.repository.UserRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ifeify
 */
public class UserRepositoryImpl implements UserRepository {
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> userCollection;

    @Inject
    public UserRepositoryImpl(MongoClient mongoClient) {
        mongoDatabase = mongoClient.getDatabase("campustradein");
        userCollection = mongoDatabase.getCollection("users");
        userCollection.createIndex(Indexes.ascending("username"));
        userCollection.createIndex(Indexes.ascending("email"));
    }

    @Override
    public Optional<Credential> getUserCredential(String usernameOrEmail) {
        if(usernameOrEmail.contains("@")) {
            Document document = userCollection.find(Filters.eq("email", usernameOrEmail)).first();
            if(document != null) {
                Credential credential = new Credential();
                credential.setUsername(document.getString("username"));
                credential.setEmail(document.getString("email"));
                credential.setEncryptedPassword(document.getString("password"));
                return Optional.of(credential);
            } else {
                return Optional.empty();
            }
        } else {
            Document document = userCollection.find(Filters.eq("username", usernameOrEmail)).first();
            if(document != null) {
                Credential credential = new Credential();
                credential.setUsername(document.getString("username"));
                credential.setEmail(document.getString("email"));
                credential.setEncryptedPassword(document.getString("password"));
                return Optional.of(credential);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public void addUser(UserAccount userAccount) throws UserAlreadyExistsException {
        try {
            Document key = new Document("username", userAccount.getUsername())
                    .append("email", userAccount.getEmail());

            Document document = new Document("_id", key)
                    .append("username", userAccount.getUsername())
                    .append("email", userAccount.getEmail())
                    .append("password", userAccount.getPassword())
                    .append("isActivated", userAccount.isActivated())
                    .append("college", userAccount.getCollege())
                    .append("memberSince", userAccount.memberSince())
                    .append("bookListings", new ArrayList<Book>());

            userCollection.insertOne(document);
        } catch(Exception e) {
            throw new UserAlreadyExistsException(e);
        }
    }

    @Override
    public void activateUser(String username) throws UserNotFoundException {
        UpdateResult updateResult = userCollection.updateOne(Filters.eq("username", username),
                                                                Updates.set("isActivated", true));
        if(updateResult.getMatchedCount() == 0) {
            throw new UserNotFoundException(username + " does not exist");
        }
    }

    private void mapToDomainModel(Document document, UserAccount userAccount) {
        userAccount.setUsername(document.getString("username"));
        userAccount.setEmail(document.getString("email"));
        userAccount.setPassword(document.getString("password"));
        userAccount.setActive(document.getBoolean("isActivated"));
        userAccount.setDateJoined(document.getDate("memberSince"));
        userAccount.setCollege(document.getString("college"));
        userAccount.setBookListing((List<Book>)document.get("bookListings"));
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        Document document = userCollection.find(Filters.eq("username", username)).first();
        if(document == null) {
            return Optional.empty();
        }
        UserAccount userAccount = new UserAccount();
        mapToDomainModel(document, userAccount);
        return Optional.of(userAccount);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        Document document = userCollection.find(Filters.eq("username", email)).first();
        if(document == null) {
            return Optional.empty();
        }
        UserAccount userAccount = new UserAccount();
        mapToDomainModel(document, userAccount);
        return Optional.of(userAccount);
    }

    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        DeleteResult deleteResult = userCollection.deleteOne(Filters.eq("username", username));
        if(deleteResult.getDeletedCount() == 0) {
            throw new UserNotFoundException(username + " does not exist or could not be deleted");
        }
    }

    @Override
    public void addBookListing(Book book) {
        Document listing = new Document("bookId", book.getBookId())
                                .append("title", book.getTitle())
                                .append("authors", book.getAuthors())
                                .append("isbn13", book.getIsbn13())
                                .append("isbn10", book.getIsbn10())
                                .append("listedBy", book.getListedBy())
                                .append("dateListedOn", book.getDateListed())
                                .append("price", book.getPrice())
                                .append("condition", book.getCondition())
                                .append("tags", book.getCategories());

        userCollection.updateOne(Filters.eq("username", book.getListedBy()),
                                        Updates.push("bookListings", listing));
    }

    @Override
    public void updateListing(Book book) {

    }

    @Override
    public void deleteListing(String username, String bookId) {
    }
}

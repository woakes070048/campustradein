package com.cti.repository.impl;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.exception.UserNotFoundException;
import com.cti.model.Book;
import com.cti.model.UserAccount;
import com.cti.repository.UserRepository2;
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

/**
 * @author ifeify
 */
public class UserRepositoryImpl implements UserRepository2 {
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
    public UserAccount findByUsername(String username) throws UserNotFoundException {
        Document document = userCollection.find(Filters.eq("username", username)).first();
        if(document == null) {
            throw new UserNotFoundException(username + " does not exist");
        }
        UserAccount userAccount = new UserAccount();
        mapToDomainModel(document, userAccount);
        return userAccount;
    }

    @Override
    public UserAccount findByEmail(String email) throws UserNotFoundException {
        Document document = userCollection.find(Filters.eq("username", email)).first();
        if(document == null) {
            throw new UserNotFoundException(email + " does not exist");
        }
        UserAccount userAccount = new UserAccount();
        mapToDomainModel(document, userAccount);
        return userAccount;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        if(userCollection.find(Filters.eq("email", email)).first() == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isUsernameRegistered(String username) {
        if(userCollection.find(Filters.eq("username", username)).first() == null) {
            return false;
        }
        return true;
    }

    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        DeleteResult deleteResult = userCollection.deleteOne(Filters.eq("username", username));
        if(deleteResult.getDeletedCount() == 0) {
            throw new UserNotFoundException(username + " does not exist or could not be deleted");
        }
    }

    @Override
    public void addNewListing(Book book) {
        userCollection.updateOne(Filters.eq("username", book.getListedBy()),
                                        new Document("$push", new Document("bookListings", book)));
    }

    @Override
    public void updateListing(Book book) {

    }

    @Override
    public void deleteListing(Book book) {

    }
}

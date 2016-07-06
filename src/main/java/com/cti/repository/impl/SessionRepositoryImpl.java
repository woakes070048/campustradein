package com.cti.repository.impl;

import com.cti.common.auth.TokenGenerator;
import com.cti.common.exception.UserNotFoundException;
import com.cti.repository.SessionRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author ifeify
 */
public class SessionRepositoryImpl implements SessionRepository {
    private MongoCollection<Document> sessionCollection;

    @Inject
    public SessionRepositoryImpl(MongoClient mongoClient) {
        sessionCollection = mongoClient.getDatabase("campustradein").getCollection("sessions");
        sessionCollection.createIndex(Indexes.ascending("session_id"));
        sessionCollection.createIndex(Indexes.ascending("username"));
    }

    @Override
    public String newSession(String username) {
        String sessionID = TokenGenerator.generate();
        Document document = new Document("username", username)
                                    .append("session_id", sessionID);
        sessionCollection.insertOne(document);
        return sessionID;
    }

    @Override
    public Optional<String> findBySessionID(String sessionID) {
        Document document = sessionCollection.find(Filters.eq("session_id", sessionID)).first();
        if(document == null) {
            return Optional.empty();
        }
        return Optional.of(document.getString("session_id"));
    }

    @Override
    public void deleteSession(String username) throws UserNotFoundException {
        DeleteResult deleteResult = sessionCollection.deleteOne(Filters.eq("username", username));
        if(deleteResult.getDeletedCount() == 0) {
            throw new UserNotFoundException(username + " has no session");
        }
    }
}

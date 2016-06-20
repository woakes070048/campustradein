package com.cti.repository.impl;

import com.cti.auth.AuthenticationToken;
import com.cti.repository.TokenRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import javax.inject.Inject;

/**
 * @author ifeify
 */
public class TokenRepositoryImpl implements TokenRepository {
    private static final long EXPIRATION_TIME = 60 * 24; // in minutes

    private MongoCollection<Document> tokenCollection;

    @Inject
    public TokenRepositoryImpl(MongoClient mongoClient) {
        tokenCollection = mongoClient.getDatabase("campustradein").getCollection("tokens");
        tokenCollection.createIndex(Indexes.ascending("username"));
    }

    @Override
    public String newToken(String username) {
        String token = AuthenticationToken.generate();
        Document document = new Document("username", username)
                                .append("token", token)
                                .append("expiryDate", EXPIRATION_TIME);
        tokenCollection.insertOne(document);
        return token;
    }

    @Override
    public String newToken(String username, long expiryDate) {
        String token = AuthenticationToken.generate();
        Document document = new Document("username", username)
                                .append("token", token)
                                .append("expiryDate", expiryDate);
        tokenCollection.insertOne(document);
        return token;
    }

    @Override
    public void deleteToken(String username) {

    }
}

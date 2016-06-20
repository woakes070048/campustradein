package com.cti.repository.impl;

import com.cti.auth.TokenGenerator;
import com.cti.exception.InvalidTokenException;
import com.cti.repository.TokenRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import javax.inject.Inject;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author ifeify
 */
public class TokenRepositoryImpl implements TokenRepository {
    private static final long EXPIRATION_TIME = 60 * 24; // in minutes

    private MongoCollection<Document> tokenCollection;

    @Inject
    public TokenRepositoryImpl(MongoClient mongoClient) {
        tokenCollection = mongoClient.getDatabase("campustradein").getCollection("tokens");
        tokenCollection.createIndex(Indexes.ascending("token"));
    }

    @Override
    public String newToken(String username) {
        LocalDateTime time = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
        String token = TokenGenerator.generate();
        Document document = new Document("username", username)
                                .append("token", token)
                                .append("expirationTime", EXPIRATION_TIME);
        tokenCollection.insertOne(document);
        return token;
    }

    @Override
    public String newToken(String username, long expirationTime) {
        // mongodb does not support LocalDateTime so convert to Date
        LocalDateTime time = LocalDateTime.now().plusMinutes(expirationTime);
        Date expirationDate = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());

        String token = TokenGenerator.generate();
        Document document = new Document("username", username)
                                .append("token", token)
                                .append("expirationDate", expirationDate);
        tokenCollection.insertOne(document);
        return token;
    }

    @Override
    public boolean hasTokenExpired(String token) throws InvalidTokenException {
        Document document = tokenCollection.find(Filters.eq("token", token)).first();
        if(document == null) {
            throw new InvalidTokenException(token + " is not valid");
        }
        Date expirationDate = document.getDate("expirationDate");
        LocalDateTime expirationTime = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());
        return expirationTime.isBefore(LocalDateTime.now());
    }

    @Override
    public String findUserByTokenId(String token) throws InvalidTokenException {
        Document document = tokenCollection.find(Filters.eq("token", token)).first();
        if(document == null) {
            throw new InvalidTokenException(token + " is not valid");
        }
        return document.getString("username");
    }


    @Override
    public void deleteToken(String token) {
        tokenCollection.deleteOne(Filters.eq("token", token));
    }
}

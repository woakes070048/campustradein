package com.cti.repository.impl;

import com.cti.auth.VerificationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.repository.TokenRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;;
import org.bson.Document;

import javax.inject.Inject;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

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
    public void addToken(VerificationToken token) throws DuplicateTokenException {
        LocalDateTime expirationTime = token.getExpirationDateTime();
        Date expirationDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
        Document document = new Document("username", token.getUsername())
                                        .append("token", token.getToken())
                                        .append("expirationDate", expirationDate);
        tokenCollection.insertOne(document);
    }

    @Override
    public Optional<VerificationToken> findById(String tokenId) {
        Document document = tokenCollection.find(Filters.eq("token", tokenId)).first();
        if(document == null) {
            return Optional.empty();
        }
        VerificationToken token = new VerificationToken();
        Date expirationDate = document.getDate("expirationDate");
        LocalDateTime expirationTime = LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault());
        token.setExpirationDateTime(expirationTime);
        token.setUsername(document.getString("username"));

        return Optional.of(token);
    }

    @Override
    public void deleteToken(String token) {
        tokenCollection.deleteOne(Filters.eq("token", token));
    }
}

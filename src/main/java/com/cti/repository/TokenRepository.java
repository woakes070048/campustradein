package com.cti.repository;

import com.cti.auth.Token;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;

import java.util.Optional;

public interface TokenRepository {

    void addToken(Token token) throws DuplicateTokenException;

    Optional<Token> findById(String token);

    void deleteToken(String token);
}

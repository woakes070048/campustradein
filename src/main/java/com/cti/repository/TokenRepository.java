package com.cti.repository;

import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;

public interface TokenRepository {

    void addToken(Token token) throws DuplicateTokenException;

    Token findById(String token) throws InvalidTokenException;

    void deleteToken(String token);
}

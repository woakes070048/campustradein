package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;

public interface TokenRepository {
	String newToken(String username);

    String newToken(String username, long expiryDate);

    void deleteToken(String username);
}

package com.cti.repository;

import com.cti.auth.VerificationToken;
import com.cti.exception.DuplicateTokenException;

import java.util.Optional;

public interface TokenRepository {

    void addToken(VerificationToken verificationToken) throws DuplicateTokenException;

    Optional<VerificationToken> findById(String token);

    void deleteToken(String token);
}

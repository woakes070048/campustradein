package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;

public interface TokenRepository {
	void save(@NotNull AuthenticationToken verificationToken) throws DuplicateTokenException;
	
	void delete(@NotNull AuthenticationToken verificationToken);
	
	void delete(@NotNull String tokenId);
	
	AuthenticationToken findByTokenId(@NotNull String tokenId) throws InvalidTokenException;
}

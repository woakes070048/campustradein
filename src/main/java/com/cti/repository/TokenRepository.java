package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;

public interface TokenRepository extends IRepository<AuthenticationToken> {
	@Override
	public void save(@NotNull AuthenticationToken verificationToken) throws DuplicateTokenException;
	
	public void delete(@NotNull AuthenticationToken verificationToken);
	
	public void delete(@NotNull String tokenId);
	
	public AuthenticationToken findByTokenId(@NotNull String tokenId) throws InvalidTokenException;
}

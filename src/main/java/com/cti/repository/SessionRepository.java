package com.cti.repository;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;
import com.cti.model.User;

public interface SessionRepository extends IRepository<AuthenticationToken> {
	@Override
	public void save(AuthenticationToken token) throws DuplicateTokenException;
	
	@Override
	public void delete(AuthenticationToken token) throws InvalidTokenException;
	
	public void delete(String token) throws InvalidTokenException;

	public User findBySessionID(String sessionID);
}

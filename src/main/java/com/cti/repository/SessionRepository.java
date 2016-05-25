package com.cti.repository;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;
import com.cti.model.User;

public interface SessionRepository {
	void save(AuthenticationToken token) throws DuplicateTokenException;

	void delete(AuthenticationToken token) throws InvalidTokenException;
	
	void delete(String token) throws InvalidTokenException;

	User findBySessionID(String sessionID) throws InvalidTokenException;
}

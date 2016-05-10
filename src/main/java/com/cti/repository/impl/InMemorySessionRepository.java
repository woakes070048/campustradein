package com.cti.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;
import com.cti.model.User;
import com.cti.repository.SessionRepository;
import com.google.inject.Singleton;

@Singleton
public class InMemorySessionRepository implements SessionRepository {
	private Map<String, User> sessions = new HashMap<>();
	
	@Override
	public void update(AuthenticationToken object) throws Exception {
		
	}

	@Override
	public void save(AuthenticationToken token) throws DuplicateTokenException {
		String sessionID = token.getToken();
		if(sessions.containsKey(sessionID)) {
			throw new DuplicateTokenException(sessionID + " is a duplicate");
		}
		sessions.put(sessionID, token.getUser());
	}

	@Override
	public void delete(AuthenticationToken token) throws InvalidTokenException {
		if(!sessions.containsKey(token.getToken())) {
			throw new InvalidTokenException(token.getToken() + " does not exist");
		}
		sessions.remove(token.getToken());
	}

	@Override
	public void delete(String token) throws InvalidTokenException {
		if(!sessions.containsKey(token)) {
			throw new InvalidTokenException(token + " does not exist");
		}
		sessions.remove(token);
	}

	@Override
	public User findBySessionID(String sessionID) throws InvalidTokenException {
		if(!sessions.containsKey(sessionID)) {
			throw new InvalidTokenException();
		}
		return sessions.get(sessionID);
	}
}

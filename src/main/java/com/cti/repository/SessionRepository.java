package com.cti.repository;

import com.cti.exception.UserNotFoundException;

public interface SessionRepository {
	String newSession(String username);

	String findBySessionID(String sessionID);

    void deleteSession(String username) throws UserNotFoundException;
}

package com.cti.repository;

import com.cti.exception.UserNotFoundException;

import java.util.Optional;

public interface SessionRepository {
	String newSession(String username);

	Optional<String> findBySessionID(String sessionID);

    void deleteSession(String username) throws UserNotFoundException;
}

package com.cti.repository;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;
import com.cti.model.UserAccount;

public interface SessionRepository {
	String newSession(String username);

	String findBySessionID(String sessionID);

    String findByUsername(String username);

    void deleteSession(String sessionID);
}

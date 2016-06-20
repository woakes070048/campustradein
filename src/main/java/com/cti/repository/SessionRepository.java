package com.cti.repository;

public interface SessionRepository {
	String newSession(String username);

	String findBySessionID(String sessionID);

    void deleteSession(String sessionID);
}

package com.cti.model;

public class Session {
	private String sessionId;
	private String username;
	
	public static Session forUser(String username) {
		// TODO: generate session id
		return new Session();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getSessionId() {
		return sessionId;
	}
}

package com.cti.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.cti.auth.AuthenticationToken;
import com.cti.exception.DuplicateTokenException;
import com.cti.exception.InvalidTokenException;
import com.cti.repository.TokenRepository;

public class InMemoryTokenRepository implements TokenRepository {
	
	private Map<String, AuthenticationToken> dataStore = new HashMap<>();

	@Override
	public void save(AuthenticationToken verificationToken) throws DuplicateTokenException {
		String tokenId = verificationToken.getToken();
		if(dataStore.containsKey(tokenId)) {
			throw new DuplicateTokenException(tokenId + " already exists");
		}
		dataStore.put(verificationToken.getToken(), verificationToken);
	}

	@Override
	public AuthenticationToken findByTokenId(String tokenId) throws InvalidTokenException {
		if(!dataStore.containsKey(tokenId)) {
			throw new InvalidTokenException(tokenId + " does not exist");
		}
		return dataStore.get(tokenId);
	}

	@Override
	public void delete(AuthenticationToken verificationToken) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AuthenticationToken object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String tokenId) {
		// TODO Auto-generated method stub
		
	}
}

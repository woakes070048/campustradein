package com.cti.repository.impl;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.UserAccount;
import com.cti.repository.UserRepository;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryUserRepository implements UserRepository {
	private Map<String, UserAccount> usernameIndex = new HashMap<>();
    private Map<String, UserAccount> emailIndex = new HashMap<>();
	private Map<String, String> sessionDB = new HashMap<>();

	@Override
	public void save(UserAccount userAccount) throws UserAlreadyExistsException {
		if(usernameIndex.containsKey(userAccount.getUsername())
                			|| emailIndex.containsKey(userAccount.getEmail())) {
			throw new UserAlreadyExistsException("Username or Email already exists");
		}
		usernameIndex.put(userAccount.getUsername(), userAccount);
        emailIndex.put(userAccount.getEmail(), userAccount);
    }

	@Override
	public UserAccount findByUsername(@NotNull String username) {
		if(usernameIndex.containsKey(username)) {
			return usernameIndex.get(username);
		}
		return null;
	}
	
	@Override
	public UserAccount findByEmail(@NotNull String email) {
		if(emailIndex.containsKey(email)) {
			return emailIndex.get(email);
		}
		return null;
	}
	
	@Override 
	public UserAccount findByUsernameAndEmail(@NotNull String username, @NotNull String email) {
		return null;
	}


	public void delete(UserAccount object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(UserAccount userAccount) {
		if(usernameIndex.containsKey(userAccount.getUsername())) {
			usernameIndex.put(userAccount.getUsername(), userAccount);
		}
		if(emailIndex.containsKey(userAccount.getEmail())) {
			emailIndex.put(userAccount.getEmail(), userAccount);
		}
		
	}

}

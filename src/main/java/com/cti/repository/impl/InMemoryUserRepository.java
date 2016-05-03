package com.cti.repository.impl;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;
import com.cti.repository.UserRepository;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryUserRepository implements UserRepository {
	private Map<String, User> usernameIndex = new HashMap<>();
    private Map<String, User> emailIndex = new HashMap<>();
	private Map<String, String> sessionDB = new HashMap<>();

	@Override
	public void save(User user) throws UserAlreadyExistsException {
		if(usernameIndex.containsKey(user.getUsername())
                			|| emailIndex.containsKey(user.getEmail())) {
			throw new UserAlreadyExistsException("Username or Email already exists");
		}
		usernameIndex.put(user.getUsername(), user);
        emailIndex.put(user.getEmail(), user);
    }

	@Override
	public User findByUsername(@NotNull String username) {
		return null;
	}
	
	@Override
	public User findByEmail(@NotNull String email) {
		
		return null;
	}
	
	@Override 
	public User findByUsernameAndEmail(@NotNull String username, @NotNull String email) {
		return null;
	}

	@Override
	public void delete(User object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

}

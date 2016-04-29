package com.cti.repository;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;

public class InMemoryUserRepository implements UserRepository {
	private Map<String, User> users = new HashMap<>();
	
	private String createNewKey(User user) {
		return user.getUsername() + user.getPassword();
	}
	
	@Override
	public void save(User user) throws UserAlreadyExistsException {
		if(users.containsKey(user.getUsername())) {
			throw new UserAlreadyExistsException("");
		}
		String uniqueKey = createNewKey(user);
		users.put(uniqueKey, user);
	}

	@Override
	public User findByUsername(@NotNull String username) {
		if(users.containsKey(username)) {
			return users.get(username);
		} else {
			return null;
		}
	}
	
	@Override
	public User findByEmail(@NotNull String email) {
		
		return null;
	}
	
	@Override 
	public User findByUsernameAndEmail(@NotNull String username, @NotNull String email) {
		return null;
	}

}

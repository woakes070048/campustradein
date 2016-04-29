package com.cti.repository;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.cti.model.User;

public class InMemoryUserRepository implements UserRepository {
	private Map<String, User> users = new HashMap<>();
	
	@Override
	public boolean save(User user) {
		if(!users.containsKey(user.getUsername())) {
			users.put(user.getUsername(), user);
			return true;
		} else {
			return false;
		}
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

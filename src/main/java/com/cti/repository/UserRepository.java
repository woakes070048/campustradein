package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;

public interface UserRepository {
	/**
	 * Persists user information in the database and ensures that the 
	 * username and/or email does not already exist
	 */
	void save(@NotNull User user) throws UserAlreadyExistsException;
	
	void update(@NotNull User user);
	
	User findByUsername(@NotNull String username);
	
	User findByEmail(@NotNull String email);
	
	User findByUsernameAndEmail(@NotNull String username, @NotNull String email);
}

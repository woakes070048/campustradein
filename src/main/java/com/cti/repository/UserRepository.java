package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;

public interface UserRepository extends IRepository<User> {
	/**
	 * Persists user information in the database and ensures that the 
	 * username and/or email does not already exist
	 */
	public void save(@NotNull User user) throws UserAlreadyExistsException;
	
	public User findByUsername(@NotNull String username);
	
	public User findByEmail(@NotNull String email);
	
	public User findByUsernameAndEmail(@NotNull String username, @NotNull String email);
}

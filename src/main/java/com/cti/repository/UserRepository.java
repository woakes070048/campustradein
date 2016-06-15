package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.UserAccount;

public interface UserRepository {
	/**
	 * Persists userAccount information in the database and ensures that the
	 * username and/or email does not already exist
	 */
	void save(@NotNull UserAccount userAccount) throws UserAlreadyExistsException;
	
	void update(@NotNull UserAccount userAccount);
	
	UserAccount findByUsername(@NotNull String username);
	
	UserAccount findByEmail(@NotNull String email);
	
	UserAccount findByUsernameAndEmail(@NotNull String username, @NotNull String email);
}

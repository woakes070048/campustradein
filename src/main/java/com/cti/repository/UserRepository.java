package com.cti.repository;

import javax.validation.constraints.NotNull;

import com.cti.model.User;

public interface UserRepository extends IRepository<User> {	
	public boolean save(@NotNull User user);
	
	public User findByUsername(@NotNull String username);
	
	public User findByEmail(@NotNull String email);
	
	public User findByUsernameAndEmail(@NotNull String username, @NotNull String email);
}

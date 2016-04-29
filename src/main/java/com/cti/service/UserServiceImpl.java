package com.cti.service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.cti.dto.UserDto;
import com.cti.model.User;
import com.cti.repository.UserRepository;

public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	
	@Inject
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User registerNewUserAccount(UserDto userDto) {
		
		return null;
	}

	@Override
	public boolean isUsernameAlreadyTaken(@NotNull String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEmailAlreadyTaken(String email) {
		// TODO Auto-generated method stub
		return false;
	}
	


}

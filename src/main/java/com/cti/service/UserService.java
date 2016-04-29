package com.cti.service;

import java.text.MessageFormat;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.cti.auth.PasswordHasher;
import com.cti.dto.UserDto;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.User;
import com.cti.repository.UserRepository;

public class UserService {
	@Inject
	private UserRepository userRepository;
	@Inject
	PasswordHasher hasher;

	@Inject
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerNewUserAccount(UserDto userDto)
			throws UserAlreadyExistsException {
		try {
			String password = hasher.generateHash(userDto.getPassword());

			User user = new User();
			user.setUsername(userDto.getUsername());
			user.setPassword(password);
			user.setEmail(userDto.getEmail());
			user.setCollege(userDto.getCollege());

			userRepository.save(user);
			return user;
		} catch (Exception e) {
			throw new UserAlreadyExistsException(MessageFormat.format(
					"{0} with email {1} is already a registered member",
					userDto.getUsername(), userDto.getEmail()), e);
		}
	}

	public boolean isUsernameAlreadyTaken(@NotNull String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return false;
		}
		return true;
	}

	public boolean isEmailAlreadyTaken(String email) {
		// TODO Auto-generated method stub
		return false;
	}

}

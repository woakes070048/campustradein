package com.cti.service;


import com.cti.dto.UserDto;
import com.cti.exception.EmailExistsException;
import com.cti.exception.UsernameExistsException;
import com.cti.model.User;

public interface UserService {
	User registerNewUserAccount(UserDto userDto);
	
	boolean isUsernameAlreadyTaken(String username);
	
	boolean isEmailAlreadyTaken(String email);
}

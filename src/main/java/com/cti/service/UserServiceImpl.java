package com.cti.service;

import com.cti.dto.UserDto;
import com.cti.exception.EmailExistsException;
import com.cti.exception.UsernameExistsException;
import com.cti.model.User;

public class UserServiceImpl implements UserService {
	@Override
	public User registerNewUserAccount(UserDto accountDto)
						throws UsernameExistsException, EmailExistsException {
		
		return null;
	}

}

package com.cti.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cti.annotation.PasswordMatches;
import com.cti.dto.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{
	@Override
	public void initialize(PasswordMatches constraintAnnotation) {}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		UserDTO user = (UserDTO)object;
		if(user.getPassword() == null && user.getMatchingPassword() == null) {
			return false;
		}
		return user.getPassword().equals(user.getMatchingPassword());
	}

}

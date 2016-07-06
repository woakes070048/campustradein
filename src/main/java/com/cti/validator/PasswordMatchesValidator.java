package com.cti.validator;

import com.cti.common.annotation.PasswordMatches;
import com.cti.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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

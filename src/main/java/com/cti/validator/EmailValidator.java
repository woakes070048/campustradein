package com.cti.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cti.annotation.ValidEmail;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^.{8,}$";
//	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+
//	        (.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*
//	        (.[A-Za-z]{2,})$"; 
	
	@Override
	public void initialize(ValidEmail constraintAnnotation) {}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}

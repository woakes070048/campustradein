package com.cti.service;

import com.cti.annotation.encrypter.PBKDF2;
import com.cti.auth.Encrypter;
import com.cti.auth.Password;
import com.cti.auth.VerificationToken;
import com.cti.dto.UserDto;
import com.cti.exception.EncryptionException;
import com.cti.exception.UserAlreadyExistsException;
import com.cti.model.Session;
import com.cti.model.User;
import com.cti.repository.UserRepository;

import javax.inject.Inject;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.ConstraintDescriptor;
import java.text.MessageFormat;
import java.util.Set;

public class UserService {
	@Inject
	private UserRepository userRepository;
	@Inject
    @PBKDF2
	private Encrypter encrypter;
    private Validator validator;

    public UserService() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

	@Inject
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
	}

	public User createUserAccount(UserDto userDto)
							throws ValidationException, UserAlreadyExistsException {
		try {
            Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
            if(violations.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for(ConstraintViolation<UserDto> violation : violations) {
                    ConstraintDescriptor desc = violation.getConstraintDescriptor();
                    stringBuilder.append(desc.getMessageTemplate());
                    stringBuilder.append(".");
                }
                throw new ValidationException(stringBuilder.toString());
            }
            Password encryptedPassword = encrypt(userDto.getPassword());
			User user = new User();
			user.setUsername(userDto.getUsername());
			user.setPassword(encryptedPassword.toString());
			user.setEmail(userDto.getEmail());
			user.setCollege(userDto.getCollege());
            user.setSessionId(Session.generate());
			userRepository.save(user);
			return user;
		} catch (Exception e) {
			throw new UserAlreadyExistsException(MessageFormat.format(
					"{0} with email {1} is already a registered member",
					userDto.getUsername(), userDto.getEmail()), e);
		}
	}

	public void createVerificationToken(User user, String token) {

    }

    public VerificationToken getVerificationToken(String token) {
        return null;
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

    private Password encrypt(String plainTextPassword) throws EncryptionException {
        return new Password.PasswordBuilder()
                .plainTextPassword(plainTextPassword)
                .useEncrypter(encrypter)
                .hash();
    }

    private Password encrypt(String plainTextPassword, Password template) throws EncryptionException {
        return new Password.PasswordBuilder()
                .plainTextPassword(plainTextPassword)
                .useSalt(template.getSalt())
                .iterations(template.getIterations())
                .hashSize(template.getHashSize())
                .useEncrypter(template.getEncrypter())
                .hash();
    }

}

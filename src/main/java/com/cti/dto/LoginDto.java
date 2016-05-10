package com.cti.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDto {
    @NotNull(message = "Username or Email not specified")
    @NotBlank(message = "Username or Email not specified")
    @Size(min = 3)
    private String usernameOrEmail;

    @NotNull(message = "Password not specified")
    @NotBlank(message = "Password not specified")
    @Size(min = 8)
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

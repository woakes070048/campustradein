package com.cti.model;

import com.cti.annotation.ValidEmail;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class UserAccount {
    @NotNull(message = "Username not specified")
    @NotBlank(message = "Username not specified")
    @Pattern(regexp = "[a-zA-Z]*", message = "Username has invalid characters")
    @Size(min = 3)
	private String username;

    @ValidEmail
    @NotNull
    @NotBlank(message = "Email not specified")
	private String email;

    @NotNull
    @NotBlank(message = "Password not specified")
    @Size(min = 8)
	private String password;

    @NotNull
    @NotBlank(message = "Please pick a college")
	private String college;

	private Date dateJoined = new Date();
	private boolean isActive = false;
	private List<Book> listings = new ArrayList<>();

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActivated() {
		return isActive;
	}
	
	public Date memberSince() {
		return dateJoined;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getCollege() {
		return college;
	}
	
	public String toString() {
		return MoreObjects.toStringHelper(UserAccount.class)
					.add("username", username)
					.add("email", email)
					.add("password", password)
					.add("college", college)
					.add("dateJoined", dateJoined)
					.add("isActive?", isActive)
					.toString();
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public void setActive(Boolean active) {
		this.isActive = active;
	}

	public void setBookListing(List<Book> bookListings) {
		this.listings = bookListings;
	}

    public List<Book> getBookListing() {
        return listings;
    }
}

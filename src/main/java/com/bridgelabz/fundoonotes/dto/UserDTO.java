package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;


public class UserDTO 
{
	@NotNull(message = "username cannot be empty")
	private String username;
	@NotNull(message = "Firstname cannot be empty")
	private String Firstname;
	@NotNull(message = "lastname cannot be empty")
	private String Lastname;
	@NotNull(message = "email cannot be empty")
	@Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]{2,4}$",message = "give an valid email")
	private String email;
	@NotNull(message = "password cannot be empty")
	@Size(min = 8,max = 20)
	private String password;
	@NotNull(message="reenter the password")
	private String passwordagain;
	
	
	
	
	public UserDTO(@NotNull(message = "username cannot be empty") String username,
			@NotNull(message = "Firstname cannot be empty") String firstname,
			@NotNull(message = "lastname cannot be empty") String lastname,
			@NotNull(message = "email cannot be empty") @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]{2,4}$", message = "give an valid email") String email,
			@NotNull(message = "password cannot be empty") @Size(min = 8, max = 20) String password) {
		this.username = username;
		Firstname = firstname;
		Lastname = lastname;
		this.email = email;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JsonIgnore
	@JsonProperty(value = "passwordagain")
	public String getPasswordagain() {
		return passwordagain;
	}
	public void setPasswordagain(String passwordagain) {
		this.passwordagain = passwordagain;
	}
	
	
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", Firstname=" + Firstname + ", Lastname=" + Lastname + ", email="
				+ email + ", password=" + password + ", passwordagain=" + passwordagain + "]";
	}
	
	
}

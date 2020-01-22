package com.bridgelabz.fundoonotes.service;

import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exceptions.LoginException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.response.Response;

public interface UserService
{
	boolean Register(UserDTO userDto) throws UserException;
	ResponseEntity<Response> login(LoginDTO logindto) throws LoginException;
	ResponseEntity<Response> verifyEmail(String jwt) throws JWTTokenException;
	String resetPassword(String password,String jwt) throws JWTTokenException;
	void forgotPassword(ForgotDTO forgotdto);

}

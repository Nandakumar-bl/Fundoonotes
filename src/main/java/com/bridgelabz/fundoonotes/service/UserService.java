package com.bridgelabz.fundoonotes.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.response.Response;

public interface UserService
{
	public boolean Register(UserDTO userDto);
	public ResponseEntity<Response> login(LoginDTO logindto);
	public void verifyEmail(String jwt);
	public String resetPassword(String password,String jwt);
	public void forgotPassword(ForgotDTO forgotdto);

}

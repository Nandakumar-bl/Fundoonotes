package com.bridgelabz.fundoonotes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exceptions.LoginException;
import com.bridgelabz.fundoonotes.exceptions.MailIDNotFoundException;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;
	@Autowired
	Utility utility;
	
	

	
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@Valid @RequestBody UserDTO userdto, BindingResult bindingresult) {
	
		if (bindingresult.hasErrors()) 
		{
		   
			return ResponseEntity.badRequest().
					body(new Response(400,"Errors_found", bindingresult.getAllErrors()));
		} 
		else 
		{
			if(service.Register(userdto))
			return ResponseEntity.ok().body(new Response(200,"Registered Successfully",userdto));
			else
			 return ResponseEntity.badRequest().body(new Response(400,"User already registered",userdto));
		}
	}
	@GetMapping("/verifyemail/{jwt}")
	public ResponseEntity<Response> checkEmail(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		service.verifyEmail(jwt);
		return ResponseEntity.ok().body(new Response(400,"Email Verified",null));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO logindto) throws LoginException{
		
		
		return service.login(logindto);
	}
	
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotDTO forgotdto) throws MailIDNotFoundException
	{
		if(utility.checkmail(forgotdto.getEmail()))
		{
		service.forgotPassword(forgotdto);
		return ResponseEntity.ok().body(new Response(400,"Mail sent to your id",forgotdto));
		}
		else
		{
			throw new MailIDNotFoundException("Not a valid mail id"); 
		}
	}
	
	@GetMapping("/resetpassword/{jwt}")
	public ResponseEntity<Response> newPassword(@RequestBody PasswordDTO password,@PathVariable("jwt") String jwt) throws JWTTokenException
	{
	
		if(password.getNewpassword().equals(password.getConfirmnewpassword()))
		{
			String resetresult=service.resetPassword(password.getNewpassword(),jwt);
			if(resetresult==null)
			return ResponseEntity.ok().body(new Response(200,"password redefined",null));
			else
			return ResponseEntity.badRequest().body(new Response(400,"Token Expired",null));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"password Not Matching",null));
		}
		
	}

}

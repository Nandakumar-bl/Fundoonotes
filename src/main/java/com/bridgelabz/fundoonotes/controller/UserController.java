package com.bridgelabz.fundoonotes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
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
	
		System.out.println(userdto);
		
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
	@GetMapping("/verifyemail")
	public ResponseEntity<Response> checkEmail(@RequestParam("jwt") String jwt)
	{
		System.out.println(jwt);
		service.verifyEmail(jwt);
		return ResponseEntity.ok().body(new Response(400,"Email Verified",null));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO logindto)
	{
		
		return service.login(logindto);
	}
	
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotDTO forgotdto)
	{
		if(utility.checkmail(forgotdto.getEmail()))
		{
		service.forgotPassword(forgotdto);
		return ResponseEntity.ok().body(new Response(400,"Mail sent to your id",forgotdto));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(200,"Mailid not found",forgotdto));
		}
	}
	
	@GetMapping("/resetpassword")
	public ResponseEntity<Response> newPassword(@RequestBody PasswordDTO password,HttpServletRequest request)
	{
	
		if(password.getNewpassword().equals(password.getConfirmnewpassword()))
		{
			String resetresult=service.resetPassword(password.getNewpassword(),request.getHeader("header"));
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

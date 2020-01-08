package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList; 
import org.modelmapper.ModelMapper;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;


@Service
public class UserImplementation implements UserService
{
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper mapper;
	@Autowired
	BCryptPasswordEncoder bcrypt;
	@Autowired
	private JavaMailSender javaMailSender;
	
	Utility utility=new Utility();
	
	
	public boolean Register(UserDTO userDto)
	  {
		  try 
		  {  
			    UserInfo user=mapper.map(userDto,UserInfo.class);
	        	repository.SaveUser(user.getId(), user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail(), bcrypt.encode(user.getPassword()));
	        	// kafkaTemplate.send("verification",user.getEmail());
	        	return true;
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  return false;
		  }		 
	  }
	
	/*
	 * @KafkaListener(topics = "verification", groupId = "group-id") public void
	 * consume(String message) { MailDetails(message); kafkaTemplate.flush();
	 * 
	 * }
	 */
	
	
	public ResponseEntity<Response> login(LoginDTO logindto)
	{
		UserInfo user=repository.findByUsername(logindto.getUsername());
		
		if(!utility.checkUser(logindto.getUsername())) 
		{
			
		}
		
		if(utility.checkverified(logindto.getUsername()))
		{
			if(logindto.getUsername().equals(user.getUsername()) && bcrypt.matches(logindto.getPassword(),user.getPassword())) 
			{
				String token=utility.generateToken(new User(logindto.getUsername(),logindto.getPassword(),new ArrayList()));
				return ResponseEntity.ok().body(new Response(200,"Token recieved",token));	
			}
			else
				return ResponseEntity.badRequest().body(new Response(400,"Bad credentials","password or username is wrong"));
		}		
		else
		{
			 return ResponseEntity.badRequest().body(new Response(400,"Not  Verified","you are not verified"));
		}
	}
	
	public void verifyEmail(String jwt)
	{
		if(utility.validateToken(jwt))
		{
			repository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
		}
	}
	
	/*
	 * @KafkaListener(topics = "Email_send", groupId = "group-id") public void
	 * consume(String message) { utility.sendEMail(message); kafkaTemplate.flush(); }
	 */
	
	
	public String resetPassword(String password,String jwt)
	{
		if(utility.validateToken(jwt))
		{
		repository.changepassword(password,jwt);
        return null;
		}
		else
		{
			return "Token Expired";
		}
	}

	@Override
	public void forgotPassword(ForgotDTO forgotdto) {
	
		kafkaTemplate.send("Email_send",forgotdto.getEmail());
		
	}
	
	

	
	

}

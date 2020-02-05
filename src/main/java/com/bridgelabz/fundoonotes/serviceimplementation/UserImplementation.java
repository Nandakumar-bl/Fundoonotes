package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
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
import com.bridgelabz.fundoonotes.exceptions.LoginException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class UserImplementation implements UserService, UserDetailsService {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	private UserRepository repository;
	private ModelMapper mapper;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	Utility utility;

	public UserImplementation() {
	}

	@Autowired
	public UserImplementation(UserRepository repository, ModelMapper mapper, Utility utility) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.utility = utility;
	}

	public boolean Register(UserDTO userDto) throws UserException {

		if (repository.findByUsername(userDto.getUsername()) != null)
			throw new UserException("username already exsist");

		try {
			System.out.println("inside register");
			UserInfo user = mapper.map(userDto, UserInfo.class);
			repository.SaveUser(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(),
					user.getEmail(), bcrypt.encode(user.getPassword()));
			//rabbitTemplate.convertAndSend("exchanger", "key", user.getEmail());
			// kafkaTemplate.send("verification",user.getEmail());
			System.out.println("after register");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// @RabbitListener(queues ="myqueue")
	public void emailVerifier(String mail) {
		utility.MailDetails(mail);
	}

	/*
	 * @KafkaListener(topics = "verification", groupId = "group-id") public void
	 * consume(String message) {
	 * 
	 * utility.MailDetails(message); kafkaTemplate.flush();
	 * 
	 * }
	 */

	public ResponseEntity<Response> login(LoginDTO logindto) throws LoginException {
		UserInfo user = repository.findByUsername(logindto.getUsername());

		if (!utility.checkUser(logindto.getUsername())) {
			throw new LoginException("register before Login");
		}

		if (utility.checkverified(logindto.getUsername())) {
			if (logindto.getUsername().equals(user.getUsername())
					&& bcrypt.matches(logindto.getPassword(), user.getPassword())) {
				String token = utility
						.generateToken(new User(logindto.getUsername(), logindto.getPassword(), new ArrayList()));
				return ResponseEntity.ok().body(new Response(200, "Token recieved", token));
			} else
				throw new LoginException("BadCredentials");
		} else {
			throw new LoginException("you not a verified User");
		}
	}

	public ResponseEntity<Response> verifyEmail(String jwt) throws JWTTokenException {
		if (utility.validateToken(jwt)) {
			repository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
			return null;
		}

		throw new JWTTokenException("Not a valid token");
	}

	/*
	 * @KafkaListener(topics = "Email_send", groupId = "group-id") public void
	 * consume2(String message) { utility.sendEMail(message); kafkaTemplate.flush();
	 * 
	 * }
	 */

	public String resetPassword(String password, String jwt) throws JWTTokenException {
		if (utility.validateToken(jwt)) {
			repository.changepassword(bcrypt.encode(password), utility.getUsernameFromToken(jwt));
			return null;
		} else {
			throw new JWTTokenException("Not a valid token");
		}
	}

	@Override
	public void forgotPassword(ForgotDTO forgotdto) {

		// kafkaTemplate.send("Email_send",forgotdto.getEmail());
		rabbitTemplate.convertAndSend("exchanger", "key", forgotdto.getEmail());

	}

	// @RabbitListener
	public void forgotPassTokenSender(String email) {
		utility.sendEMail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = repository.findByUsername(username);
		return new User(user.getUsername(), user.getPassword(), new ArrayList());

	}

}

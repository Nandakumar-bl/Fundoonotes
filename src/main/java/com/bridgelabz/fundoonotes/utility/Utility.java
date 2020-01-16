package com.bridgelabz.fundoonotes.utility;

import java.util.ArrayList;
import java.util.Date; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.Images;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

@Component
public class Utility {
	private String SECRET_KEY = "SECRET";
	@Autowired
	private JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	
	


	@Autowired
	public Utility(UserRepository userRepository)
	{
		this.userRepository=userRepository;
	}
	
	

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserDetails userdetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userdetails.getUsername());

	}

	public String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		System.out.println(username);
		System.out.println(userDetails.getUsername());
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	public Boolean validateToken(String token) {
		return (!isTokenExpired(token));
	}

	public void sendMail(String toEmail, String subject, String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setFrom("Nandhukavi100@gmail.com");
		System.out.println(mailMessage);
		javaMailSender.send(mailMessage);
	}
	
	public void sendEMail(String email)
	{
		UserInfo user=userRepository.findByEmail(email);
		String jwt=generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8082/user/resetpassword/"+jwt;
		sendMail(email,"changing password",url);
	}
	
	public boolean checkverified(String username)
	{
		UserInfo user=userRepository.findByUsername(username);
		return user.getIsemailverified();
	}

	public boolean checkUser(String username)
	{
		return userRepository.findByUsername(username)!=null;
	}
   
	public void MailDetails(String email)
	{
		System.out.println(email);
		System.out.println(userRepository);
		UserInfo user=userRepository.findByEmail(email);
		System.out.println(user);
		String jwt=generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8083/user/verifyemail/"+jwt;
		sendMail(email,"verifying email",url);
	}
	public boolean checkJWT(String token)
	{
		return validateToken(token);
	}
	public boolean checkmail(String email)
	{
		return userRepository.findByEmail(email)!=null;
	}
	
	public Notes getUpdatedNote(UpdateNoteDTO updatedto,Notes note,List<Images> images,List<Label> labels)
	{
		
		note.setIsarchieve(updatedto.isIsarchieve());
		note.setIstrash(updatedto.isIstrash());
		note.setIspinned(updatedto.isIspinned());
		note.setColor(updatedto.getColor());
		note.setTitle(updatedto.getTitle());
		note.setTakeanote(updatedto.getTakeanote());
		note.setImages(images);
		note.setLabel(labels);
		note.setReminder(updatedto.getReminder());
		
		return note;
		
	}
	
	@Cacheable(value = "mycache",key="#jwt")
	public UserInfo getUser(String jwt)
	{
		System.out.println("inside cache");
		UserInfo user=userRepository.findByUsername(getUsernameFromToken(jwt));
		return user;
		
	}
	
	@CacheEvict(value = "mycache", key="#jwt")
	public void cache(String jwt)
	{	
	}
	
	public boolean checkCollaborator(Notes note,String email)
	{
		if(userRepository.getCollaborator(email,note)!=null)
			return false;
		else
			return true;
		
	}
	
	public List getErrors(BindingResult result,UserDTO userdto)
	{
		List error=result.getAllErrors().stream().map(s->s.getDefaultMessage()).collect(Collectors.toList());
		if(!userdto.getPassword().equals(userdto.getPasswordagain()))
		{
			error.add("password not matching");
			return error;
		}
		else
			return error;
	}
	
	public boolean checkImage(String type)
	{
		String trimmed=type.substring(type.lastIndexOf("."));
		if(trimmed.equals(".png") || trimmed.equals(".jpeg") || trimmed.equals(".webp") || trimmed.equals(".bat"))
			return true;
		else
			return false;
	}

	



}

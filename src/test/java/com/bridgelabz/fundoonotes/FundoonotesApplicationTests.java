package com.bridgelabz.fundoonotes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exceptions.LoginException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.service.UserService;

@SpringBootTest
class FundoonotesApplicationTests {
	static {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	@Autowired
	UserService userservice;

	@Test
	public void testsave() throws UserException {
		assertEquals(true,
				userservice.Register(new UserDTO("naveen", "naveen", "kumar", "naveenkumar@gmail.com", "13213233")));
	}

	@Test
	public void testLogin() throws LoginException {
		assertEquals(200, userservice.login(new LoginDTO("nandhukavi", "nandhukavi")).getStatusCodeValue());
	}

}

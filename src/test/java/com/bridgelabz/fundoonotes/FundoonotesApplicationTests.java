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

}

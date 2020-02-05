package com.bridgelabz.fundoonotes;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.bridgelabz.fundoonotes.controller.UserController;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UserServiceTesting {

	static {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}
	

	private MockMvc mockMvc;
	
	@Autowired
	UserController control;
	
	@Autowired
	ObjectMapper mapper;
		
	@BeforeEach
	public void init()
	{
		mockMvc=MockMvcBuilders.standaloneSetup(control).build();
	}
	
	
	@Test
	public void registerTester() throws Exception
	{
	
		String register="{\n" + 
				"	\"username\":\"nandhuPa\",\n" + 
				"	\"email\":\"nandhukavi100@gmail.com\",\n" + 
				"	\"firstname\":\"nandhu\",\n" + 
				"	\"lastname\":\"kumar\",\n" + 
				"	\"password\":\"nandhukavi\",\n" + 
				"	\"passwordagain\":\"nandhukavi\"\n" + 
				"}";
	
		HttpHeaders headers=new HttpHeaders();
		
		mockMvc.perform(post("/user/register")
				.header("jwt","jvjv")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(register))
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void loginTest() throws Exception
	{
		String log="{\n" + 
				"\n" + 
				"\"username\":\"nandhukavi\",\n" + 
				"\"password\":\"nandhukavi\"\n" + 
				"}"; 
		mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(log))
				.andExpect(status().isOk());
	}


}

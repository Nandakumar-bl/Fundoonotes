package com.bridgelabz.fundoonotes;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.bridgelabz.fundoonotes.controller.NoteController;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@SpringBootTest
public class NoteTesting 
{
	static {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}
 
	private MockMvc mockMvc;
	
	@Autowired
	NoteController control;
	
	
	
	@BeforeEach
	public void init()
	{
		mockMvc=MockMvcBuilders.standaloneSetup(control).build();
	}
	
	
	//@Test
	public void saveNoteTesting() throws Exception
	{
		
		String sample="{\n" + 
				"	\"title\":\"tester title\",\n" + 
				"	\"takeanote\":\"note efces is wefcewfc taken\",\n" + 
				"	\"color\":\"blue\",\n" + 
				"	\"isarchieve\":\"false\",\n" + 
				"	\"reminder\":\"jan 10 2019\",\n" + 
				"	\"labels\":[\"hello\",\"dsjinse\",\"effe\"]\n" + 
				"}";
		
	    mockMvc.perform(post("/note/new")
	    .header("jwt","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYW5kaHVrYXZpIiwiZXhwIjoxNTgwODM4NTMyLCJpYXQiOjE1ODA4MDI1MzJ9.ntNpOcErcQYkFRrBg2c438XHrwDPRB36k78ScxFYBEw")
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .content(sample))
	    .andExpect(status().isOk());
	}
	
	@Test
	public void updateNoteTesting() throws Exception
	{
		
		String sample="{\n" + 
				"	\"id\":\"8\",\n" + 
				"	\"title\":\"zg\",\n" + 
				"	\"takeanote\":\"note efces is wefcewfc taken\",\n" + 
				"	\"color\":\"blue\",\n" + 
				"	\"isarchieve\":\"false\",\n" + 
				"	\"reminder\":\"jan 10 2019\",\n" + 
				"	\"label\":[\"hello\",\"dsjivcnse\",\"effe\"]\n" + 
				"}";
		
	    mockMvc.perform(put("/note/update")
	    .header("jwt","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYW5kaHVrYXZpIiwiZXhwIjoxNTgwODM4NTMyLCJpYXQiOjE1ODA4MDI1MzJ9.ntNpOcErcQYkFRrBg2c438XHrwDPRB36k78ScxFYBEw")
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .content(sample))
	    .andExpect(status().isOk());
	}
	
	
	
}

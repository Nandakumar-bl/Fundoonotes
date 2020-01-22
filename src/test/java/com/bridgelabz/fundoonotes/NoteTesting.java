package com.bridgelabz.fundoonotes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.service.NoteService;

@SpringBootTest
public class NoteTesting 
{
	

	static {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}
    @Autowired	
	NoteService service;
	
    
    @Test
    public void testNewNote() throws JWTTokenException, UserException, Exception
    {
    	assertEquals(true,service.saveNewNoteImpl(new NoteDTO("newtitle","testnote","violet","morning everyday",null,null),"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYW5kaHVrYXZpIiwiZXhwIjoxNTc5NzM0NjI4LCJpYXQiOjE1Nzk2OTg2Mjh9.2zCRsGCSOHQ_uOKi7xcorJu8GxUsLpTDWQvBGPOHHgk"));
    }
    
    
}

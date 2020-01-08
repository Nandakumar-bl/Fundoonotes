package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NoteController 
{
	@Autowired
	NoteService noteservice;
	@PostMapping("/newnote")
	public void newNote(@RequestBody NoteDTO notedto)
	{
		noteservice.saveNewNote(notedto);
		
		
	}
	
	

}

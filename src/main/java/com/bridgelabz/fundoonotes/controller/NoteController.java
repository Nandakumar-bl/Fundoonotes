package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
@RequestMapping("note")
public class NoteController 
{
	@Autowired
	NoteService noteservice;
	
	@PostMapping("/new")
	public ResponseEntity<Response> newNote(@RequestBody NoteDTO notedto,@RequestHeader("jwt") String jwt) throws JWTTokenException
	{
		if(noteservice.saveNewNote(notedto,jwt))
			return ResponseEntity.ok().body(new Response(200,"Note Created",notedto));
		else
			return ResponseEntity.badRequest().body(new Response(400,"problem in creating note",notedto));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable int id,@RequestHeader("jwt") String jwt) throws JWTTokenException
	{
	
		noteservice.deleteNote(id,jwt);
		return ResponseEntity.ok().body(new Response(200,"Note Deleted","Deleted note:"+id));
		
	}
	
	

}

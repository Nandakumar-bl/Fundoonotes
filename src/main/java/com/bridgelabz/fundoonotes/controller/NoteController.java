package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UpdatingNoteException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	NoteService noteservice;
	@Autowired
	Utility utility;

	@PostMapping("/new")
	public ResponseEntity<Response> newNote(@RequestBody NoteDTO notedto, @RequestHeader("jwt") String jwt)
			throws JWTTokenException, UserException {
		if (noteservice.saveNewNote(notedto, jwt)) {
			return ResponseEntity.ok().body(new Response(200, "Note Created", notedto));
		} else
			return ResponseEntity.badRequest().body(new Response(400, "problem in creating note", notedto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable int id, @RequestHeader("jwt") String jwt)
			throws JWTTokenException, NoteNotFoundException {

		noteservice.deleteNote(id, jwt);
		return ResponseEntity.ok().body(new Response(200, "Note Deleted", "Deleted note:" + id));

	}

	@PutMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updatedto, @RequestHeader("jwt") String jwt)
			throws Exception {

		if (noteservice.updateNote(updatedto, jwt)) {
			return ResponseEntity.ok().body(new Response(200, "Your note is updated successfully", updatedto));
		} else {
			throw new UpdatingNoteException("problem with updating your notes");
		}
	}

	@PostMapping("/getnote/{id}")
	public ResponseEntity<Response> getNoteById(@PathVariable("id") int id) throws NoteNotFoundException
	{
		if(noteservice.getNote(id)!=null)
			return ResponseEntity.ok().body(new Response(200, "Your note fetched",noteservice.getNote(id)));
		else
			throw new NoteNotFoundException("Note Not available for this id");
	}
	
	
	@PostMapping("/getall")
	public ResponseEntity<Response> getAllNote(@RequestHeader("jwt") String jwt ) throws NoteNotFoundException
	{
		if(noteservice.getAllNote(jwt)!=null)
			return ResponseEntity.ok().body(new Response(200, "Your note fetched",noteservice.getAllNote(jwt)));
		else
			throw new NoteNotFoundException("No Notes available for this id");
	}
	
	
	@PostMapping("/getarchieve")
	public ResponseEntity<Response> getAllArchieve(@RequestHeader("jwt") String jwt) throws NoteNotFoundException
	{
		if(noteservice.getAllArchieve(jwt)!=null)
			return ResponseEntity.ok().body(new Response(200, "Archieve Notes",noteservice.getAllArchieve(jwt)));
		else
			throw new NoteNotFoundException("No Archieve notes");
			
	}
	
	

}

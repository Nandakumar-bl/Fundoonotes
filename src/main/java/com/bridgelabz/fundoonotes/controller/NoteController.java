package com.bridgelabz.fundoonotes.controller;

import java.io.IOException; 
import java.util.List;

import org.elasticsearch.action.delete.DeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	NoteService noteservice;
	@Autowired
	Utility utility;
	@Autowired
	ElasticSearchService Eservice;

	@PostMapping("/new")
	public ResponseEntity<Response> newNote(@RequestBody NoteDTO notedto, @RequestHeader("jwt") String jwt)
			throws JWTTokenException, Exception {
		if (noteservice.saveNewNoteImpl(notedto, jwt)) {
			return ResponseEntity.ok().body(new Response(200, "Note Created", notedto));
		} else
			return ResponseEntity.badRequest().body(new Response(400, "problem in creating note", notedto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable int id, @RequestHeader("jwt") String jwt)
			throws JWTTokenException, NoteNotFoundException {

		noteservice.deleteNoteImpl(id, jwt);
		return ResponseEntity.ok().body(new Response(200, "Note Deleted", "Deleted note:" + id));

	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updatedto, @RequestHeader("jwt") String jwt)
			throws Exception {

		if (noteservice.updateNoteImpl(updatedto, jwt)) {
			return ResponseEntity.ok().body(new Response(200, "Your note is updated successfully", updatedto));
		} else {
			throw new UpdatingNoteException("problem with updating your notes");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getNoteById(@PathVariable("id") int id) throws NoteNotFoundException
	{
		NoteDTO note=noteservice.getNoteImpl(id);
		if(note!=null)
			return ResponseEntity.ok().body(new Response(200, "Your note fetched",note));
		else
			throw new NoteNotFoundException("Note Not available for this id");
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<Response> getAllNote(@RequestHeader("jwt") String jwt ) throws NoteNotFoundException
	{
		List<NoteDTO> notes=noteservice.getAllNoteImpl(jwt);
		if(notes!=null)
			return ResponseEntity.ok().body(new Response(200, "Your note fetched",notes));
		else
			throw new NoteNotFoundException("No Notes available for this id");
	}
	
	
	@PostMapping("/archieve")
	public ResponseEntity<Response> getAllArchieve(@RequestHeader("jwt") String jwt) throws NoteNotFoundException
	{
		List<NoteDTO> notes=noteservice.getAllArchieveImpl(jwt);
		if(notes!=null)
			return ResponseEntity.ok().body(new Response(200, "Archieve Notes",notes));
		else
			throw new NoteNotFoundException("No Archieve notes");
			
	}
	
	@GetMapping("/pinned")
	public ResponseEntity<Response> getAllPinnedNotes(@RequestHeader("jwt") String jwt) throws NoteNotFoundException
	{
		List<NoteDTO> notes=noteservice.getAllPinnedImpl(jwt);
		if(notes!=null)
			return ResponseEntity.ok().body(new Response(200, "Archieve Notes",notes));
		else
			throw new NoteNotFoundException("No Pinned notes available");
			
			
	}
	@GetMapping("/trash")
	public ResponseEntity<Response> getAlltrashNotes(@RequestHeader("jwt") String jwt) throws NoteNotFoundException
	{
		List<NoteDTO> notes=noteservice.getAllTrashNotesImpl(jwt);
		if(notes!=null)
			return ResponseEntity.ok().body(new Response(200, "Archieve Notes",notes));
		else
			throw new NoteNotFoundException("No trash notes available");		
	}
	
	@DeleteMapping("/emptybin")
	public ResponseEntity<Response> emptyBin(@RequestHeader("jwt") String jwt) throws NoteNotFoundException
	{
		noteservice.emptyTheBin(jwt);
		return ResponseEntity.ok().body(new Response(200,"Bin cleared","username:"+utility.getUsernameFromToken(jwt)));
	}
	
	@GetMapping("/elastictitle/{text}")
	public ResponseEntity<Response> elasticSearch(@PathVariable String text, @RequestHeader String jwt)
			throws Exception {

		Object notes = Eservice.getAllNotes();
		if (notes != null)
			return ResponseEntity.ok().body(new Response(200, "Notes are:",notes));
		else
			throw new NoteNotFoundException("no note available");
	}

	@GetMapping("/sorted")
	public Object sortedNote(@RequestHeader("jwt") String jwt) throws Exception {

		NoteDTO[] sortedNotes=noteservice.sortedNotes(jwt);
		if (sortedNotes != null)
			return ResponseEntity.ok().body(new Response(200, "sorted Notes are:", sortedNotes));
		else
			throw new NoteNotFoundException("no note available");

	}
	
	
	

}

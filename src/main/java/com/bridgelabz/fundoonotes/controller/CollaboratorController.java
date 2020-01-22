package com.bridgelabz.fundoonotes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.CollaboratorService;

@RestController 
@RequestMapping("/collaborator")
public class CollaboratorController 
{
	CollaboratorService service;   
	@Autowired
	public CollaboratorController(CollaboratorService service) {
		
		this.service = service;
	}

	@PostMapping("/")
	public ResponseEntity<Response> addCollaborator(@RequestBody CollaboratorDTO collaboratordto,@RequestHeader("jwt") String jwt) throws Exception
	{
		service.addCollaborator(collaboratordto, jwt);
		return ResponseEntity.ok().body(new Response(200,"collaborator added",collaboratordto));
	}
	
	@DeleteMapping("/")
	public ResponseEntity<Response> deleteCollaborator(@RequestBody CollaboratorDTO collaboratordto,@RequestHeader("jwt") String jwt) throws CollaboratorNotFoundException
	{
		service.deleteCollaboratorImpl(collaboratordto,jwt);
		return ResponseEntity.ok().body(new Response(200, "Successfully deleted",collaboratordto));
	}
	
	@GetMapping("/{noteid}")
	public ResponseEntity<Response> getCollaborator(@PathVariable("noteid") int id) throws CollaboratorNotFoundException, NoteNotFoundException
	{
	
		List collaborators=service.getCollaboratorByNoteId(id);
		return ResponseEntity.ok().body(new Response(200,"your collaborators fetched", collaborators));
	}

}

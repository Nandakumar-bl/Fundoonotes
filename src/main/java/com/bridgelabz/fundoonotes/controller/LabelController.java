package com.bridgelabz.fundoonotes.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.exceptions.LabelAlreadyExsistException;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.LabelUpdatingException;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
@RequestMapping("/label")
public class LabelController 
{
	
	LabelService labelservice;
	

	@Autowired
	public LabelController(LabelService labelservice)
	{
		this.labelservice=labelservice;
	}
	
	@PostMapping("/new")
	public ResponseEntity<Response> newLabel(@RequestBody LabelDTO labeldto,@RequestHeader("jwt") String jwt) throws LabelAlreadyExsistException
	{
	
		if(labelservice.createLabel(labeldto,jwt).equals("success"))
				return ResponseEntity.ok().body(new Response(200,"Note Created",labeldto));
		else
			return ResponseEntity.badRequest().body(new Response(400,"Labels not  Created",labeldto));
	}
	
	
	  @PutMapping("/update")
	  public ResponseEntity<Response>  updateLabel(@RequestBody LabelDTO labeldto) throws LabelUpdatingException, LabelAlreadyExsistException
	  {
		  if(labelservice.updateLabel(labeldto).equals("success"))
		    return ResponseEntity.ok().body(new Response(200,"Label updated",labeldto));
		  else
			  throw new LabelUpdatingException("Your label is not updated");
	  }
	  
	  @DeleteMapping("/{id}")
	  public ResponseEntity<Response>  deleteLabel(@PathVariable("id") int id) throws LabelNotFoundException
	  {
		  if(labelservice.deletelabel(id))
			  return ResponseEntity.ok().body(new Response(200,"succesfully deleted",null));
		  else
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400,"label not deleted",null));
		  
	  }
	  
	  @GetMapping("/{id}")
		public ResponseEntity<Response> getLabelById(@PathVariable("id") int id) throws LabelNotFoundException
		{
		  LabelDTO label=labelservice.getLabel(id);
			if(label!=null)
				return ResponseEntity.ok().body(new Response(200, "Your Labels fetched",label));
			else
				throw new LabelNotFoundException("Label Not available for this id");
		}
		
		
		@GetMapping("/all")
		public ResponseEntity<Response> getAllLabel(@RequestHeader("jwt") String jwt ) throws LabelNotFoundException
		{
			List<LabelDTO> labels=labelservice.getAllUserLabels(jwt);
			if(labels!=null)
				return ResponseEntity.ok().body(new Response(200, "Your Labels fetched",labels));
			else
				throw new LabelNotFoundException("No Label available for this id");
		}
		
		
		@GetMapping("/{noteid}")
		public ResponseEntity<Response> getLabelByNote(@PathVariable("noteid") int id,@RequestHeader("jwt") String jwt) throws LabelNotFoundException
		{
			List<LabelDTO> labels=labelservice.findbynoteid(id);
			if(labels!=null)
	            return ResponseEntity.ok().body(new Response(200, "Your Labels fetched",labels));
			else
				throw new LabelNotFoundException("No Label available for this note id");
		}
		
		
		
	 
}

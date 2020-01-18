package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.Map;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ElasticSearchService 
{
	
	
	public void newNote(NoteDTO note) throws IOException;
	
	public void updateNote(NoteDTO note) throws JsonProcessingException, Exception;
	
	public void deleteNote(int id);
	
	public Object getnote(String id) throws IOException;
	
}

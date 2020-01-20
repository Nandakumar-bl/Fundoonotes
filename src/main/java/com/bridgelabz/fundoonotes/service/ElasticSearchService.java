package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ElasticSearchService 
{
	
	
	public void newNote(NoteDTO note) throws IOException;
	
	public void updateNote(NoteDTO note) throws JsonProcessingException, Exception;
	
	public void deleteNote(int id);
	
	public Map getnote(String id) throws IOException;
	
	public List<NoteDTO> getMatchedNote(String text) throws Exception ;
	
	public List<NoteDTO> getAllNotes() throws IOException;
	
}

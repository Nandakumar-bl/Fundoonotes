package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse.Result;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ElasticSearchService 
{
	
	
	Result newNote(NoteDTO note) throws IOException;
	
	Result updateNote(NoteDTO note) throws JsonProcessingException, IOException;
	
	Result deleteNote(int id);
	
	NoteDTO getnote(String id) throws IOException;
	
	List<NoteDTO> getMatchedNote(String text) throws IOException ;
	
	List<NoteDTO> getAllNotes() throws IOException;
	
}

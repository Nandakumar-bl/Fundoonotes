package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.JWTTokenException;

public interface NoteService {
	
	public boolean saveNewNote(NoteDTO notedto,String jwt) throws JWTTokenException, UserException;

	public void deleteNote(int id,String jwt) throws JWTTokenException, NoteNotFoundException;
	
	public boolean updateNote(UpdateNoteDTO updatedto,String jwt)throws NoteNotFoundException,JWTTokenException;
	
	public Notes getNote(int id) throws NoteNotFoundException;

	public List<Notes> getAllNote(String jwt);
	
	public List<Notes> getAllArchieve(String jwt);

}

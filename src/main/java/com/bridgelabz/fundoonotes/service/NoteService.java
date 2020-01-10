package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.response.JWTTokenException;

public interface NoteService {
	
	public boolean saveNewNote(NoteDTO notedto,String jwt) throws JWTTokenException;

	public void deleteNote(int id,String jwt) throws JWTTokenException;

}

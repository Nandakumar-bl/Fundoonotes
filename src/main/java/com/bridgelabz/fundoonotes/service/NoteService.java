package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface NoteService {
	
	public boolean saveNewNoteImpl(NoteDTO notedto,String jwt) throws JWTTokenException, UserException, Exception;

	public void deleteNoteImpl(int id,String jwt) throws JWTTokenException, NoteNotFoundException;
	
	public boolean updateNoteImpl(UpdateNoteDTO updatedto,String jwt)throws NoteNotFoundException,JWTTokenException, JsonProcessingException, Exception;
	
	public NoteDTO getNoteImpl(int id) throws NoteNotFoundException;

	public List<NoteDTO> getAllNoteImpl(String jwt);
	
	public List<NoteDTO> getAllArchieveImpl(String jwt);

	public List<NoteDTO> getAllPinnedImpl(String jwt);

	List<NoteDTO> getAllTrashNotesImpl(String jwt);

	public void emptyTheBin(String jwt) throws NoteNotFoundException;
	
	public NoteDTO[] sortedNotes(String jwt) ;
	


}

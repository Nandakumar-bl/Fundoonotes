package com.bridgelabz.fundoonotes.service;

import java.util.List;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
   
public interface NoteService {
	
	boolean saveNewNoteImpl(NoteDTO notedto,String jwt) throws JWTTokenException, UserException, Exception;

	void deleteNoteImpl(int id,String jwt) throws JWTTokenException, NoteNotFoundException;
	
	NoteDTO updateNoteImpl(UpdateNoteDTO updatedto,String jwt)throws NoteNotFoundException,JWTTokenException, JsonProcessingException, Exception;
	
	NoteDTO getNoteImpl(int id) throws NoteNotFoundException;

	List<NoteDTO> getAllNoteImpl(String jwt,int pageNo);
	
	List<NoteDTO> getAllArchieveImpl(String jwt);

	List<NoteDTO> getAllPinnedImpl(String jwt);

	List<NoteDTO> getAllTrashNotesImpl(String jwt);
	
	boolean restoreNoteImpl(int id);

	void emptyTheBin(String jwt) throws NoteNotFoundException;
	
	NoteDTO[] sortedNotes(String jwt) ;
	


}

package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDTO;

public interface NoteService {
	
	public void saveNewNote(NoteDTO notedto,String jwt);

}

package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.model.Collaborator;

public interface CollaboratorService {
	
	void addCollaborator(CollaboratorDTO collaboratordto, String jwt) throws Exception;
	
	void deleteCollaboratorImpl(CollaboratorDTO collaboratordto, String jwt) throws CollaboratorNotFoundException;

	List<CollaboratorDTO> getCollaboratorByNoteId(int noteid) throws CollaboratorNotFoundException, NoteNotFoundException;
	
}

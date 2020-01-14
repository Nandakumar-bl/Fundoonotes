package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.model.Collaborator;

public interface CollaboratorService {
	
	public void addCollaborator(CollaboratorDTO collaboratordto, String jwt) throws Exception;
	
	public void deleteCollaboratorImpl(CollaboratorDTO collaboratordto, String jwt) throws CollaboratorNotFoundException;

	public List<Collaborator> getCollaboratorByNoteId(int noteid) throws CollaboratorNotFoundException, NoteNotFoundException;
	
}

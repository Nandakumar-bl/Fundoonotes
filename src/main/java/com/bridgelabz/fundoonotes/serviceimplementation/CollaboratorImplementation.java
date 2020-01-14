package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.exceptions.CollaboratorNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.EmailAlreadyExsist;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserException;
import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.service.CollaboratorService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class CollaboratorImplementation implements CollaboratorService
{

	CollaboratorRepository repository;
	Utility utility;
	
	@Autowired
	public CollaboratorImplementation(CollaboratorRepository repository, Utility utility) 
	{
		this.repository = repository;
		this.utility = utility;
	}


	public void addCollaborator(CollaboratorDTO collaboratordto, String jwt) throws Exception {
		UserInfo user = utility.getUser(jwt);
		
		if (repository.findByEmail(collaboratordto.getCollaborator()) != null
				&& !user.getEmail().equals(collaboratordto.getCollaborator())) {
			Notes note = repository.getNotes(collaboratordto.getNoteid());
			if (utility.checkCollaborator(note, collaboratordto.getCollaborator())) {
				Collaborator collaborator = new Collaborator(collaboratordto.getCollaborator(), note);
				repository.save(collaborator);
			} else
				throw new EmailAlreadyExsist("Mail id already Collaborated");

		} else {
			throw new UserException("Enter a valid email id");
		}
	}
	public void deleteCollaboratorImpl(CollaboratorDTO collaboratordto, String jwt) throws CollaboratorNotFoundException
	{
		if(repository.getCollaborator(collaboratordto.getCollaborator(),  collaboratordto.getNoteid())!=null)
		   repository.deleteCollaboratorRepo(collaboratordto.getCollaborator(), collaboratordto.getNoteid());
		else
			throw new CollaboratorNotFoundException("No collaborator found");
		
	}
	
	public List<Collaborator> getCollaboratorByNoteId(int noteid) throws CollaboratorNotFoundException, NoteNotFoundException
	{
		
		Notes note=repository.getNotes(noteid);
	
		if(note==null) throw new NoteNotFoundException("No note available for this id");
		
		if(note.getCollaborators()!=null)
			return note.getCollaborators();
		else
			throw new CollaboratorNotFoundException("No collaborator found");
			
	}
	
	

}

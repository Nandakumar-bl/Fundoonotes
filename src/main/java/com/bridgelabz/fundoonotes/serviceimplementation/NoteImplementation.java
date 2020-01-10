package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;   
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Images;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class NoteImplementation implements NoteService
{
	NoteRepository repository;	
	Utility utility;
	
	@Autowired
	public NoteImplementation(NoteRepository repository,Utility utility) 
	{
		this.repository=repository;
		this.utility=utility;
	}
	
	
	public boolean saveNewNote(NoteDTO notedto,String jwt) throws JWTTokenException
	{	
		
		UserInfo user=repository.findByUsername(utility.getUsernameFromToken(jwt));
		if(utility.validateToken(jwt) && user!=null)
		{
		int notesid;
		try
		{
			notesid=repository.giveMaxId()+1;
		}
		catch (NullPointerException e) 
		{
			notesid=1;
		}
		
		
		List<Label> labels=getLabels(notedto);
		List<Images> images=getImages(notedto,notesid); 
		Notes notes=new Notes(notedto.getTitle(),notedto.getTakeanote(),notedto.getReminder(),notedto.getColor(),labels,images,user);
		repository.save(notes);
		return true;
		}
		else
		{
			throw new JWTTokenException("Your Token is Not valid");
		}
	
		
	}
	
	
	public List<Images> getImages(NoteDTO notedto,int notesid)
	{

	
	    notedto.getImages().stream().map(s-> repository.createImages(s,notesid));
	    List<Images> images=repository.getImages(notesid);
	    return images;
	}
	
	
	public List<Label> getLabels(NoteDTO notedto)
	{
		int size=notedto.getLabel().size(); 
		for(int i=0;i<size;i++)
		{
			if(repository.getLabel(notedto.getLabel().get(i))==null)
			{
				repository.createLabel(notedto.getLabel().get(i));
			}
		}
		List<Label> labels=notedto.getLabel().stream().map(s-> repository.findLabelByName(s)).collect(Collectors.toList());
		
		return labels;
	}
	
	public void deleteNote(int id,String jwt) throws JWTTokenException
	{
		UserInfo user=repository.findByUsername(utility.getUsernameFromToken(jwt));
		if(utility.validateToken(jwt) && user!=null)
		{
		repository.deleteByNoteid(id);
		}
		else
		{
			throw new JWTTokenException("Your Token is Not valid");
		}
	}


	
}

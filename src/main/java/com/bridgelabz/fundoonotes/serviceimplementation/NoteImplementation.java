package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;   
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Images;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;

@Service
public class NoteImplementation implements NoteService
{
	@Autowired
	NoteRepository repository;	
	
	
	public void saveNewNote(NoteDTO notedto)
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
		List<UserInfo> collaborators=getCollaborators(notedto);
		
		repository.saveNote(notedto.getTitle(),notedto.getTakeanote(),notedto.getReminder(),labels ,images,new ArrayList<UserInfo>());
	
		
	}
	
	public List<UserInfo> getCollaborators(NoteDTO notedto)
	{
		try {
		List<UserInfo> collaborators=notedto.getCollabarotor().stream().map(s->repository.getCollaborators(s)).collect(Collectors.toList());
		return collaborators;	
		}
		catch (Exception e) 
		{
			return new ArrayList<UserInfo>();
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
}

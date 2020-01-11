package com.bridgelabz.fundoonotes.serviceimplementation;
   
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.dto.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
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
		
		
		List<Label> labels=getLabels(notedto.getLabel(),jwt);
		List<Images> images=getImages(notedto.getImages(),notesid); 
		Notes notes=new Notes(notedto.getTitle(),notedto.getTakeanote(),notedto.getReminder(),notedto.getColor(),labels,images,user);
		repository.save(notes);
		return true;
		}
		else
		{
			throw new JWTTokenException("Your Token is Not valid");
		}
	
		
	}
	
	
	public List<Images> getImages(List<String> dtoimages,int notesid)
	{

	
	    dtoimages.stream().map(s-> repository.createImages(s,notesid));
	    List<Images> images=repository.getImages(notesid);
	    return images;
	}
	
	
	public List<Label> getLabels(List<String> dtolabels,String jwt)
	{
		int size=dtolabels.size(); 
		UserInfo user=repository.findByUsername(utility.getUsernameFromToken(jwt));
		for(int i=0;i<size;i++)
		{
			if(repository.getLabel(dtolabels.get(i))==null)
			{
				repository.createLabel(dtolabels.get(i),user);
			}
		}
		List<Label> labels=dtolabels.stream().map(s-> repository.findLabelByName(s)).collect(Collectors.toList());
		
		return labels;
	}
	
	public void deleteNote(int id,String jwt) throws JWTTokenException, NoteNotFoundException
	{
		UserInfo user=repository.findByUsername(utility.getUsernameFromToken(jwt));
		if(utility.validateToken(jwt) && user!=null)
		{
		if(repository.deleteByNoteid(id)==null)
			throw new NoteNotFoundException("No Note available with this ID");
		}
		else
		{
			throw new JWTTokenException("Your Token is Not valid");
		}
	}


	public boolean updateNote(UpdateNoteDTO updatedto,String jwt) throws NoteNotFoundException,JWTTokenException
	{
		if(utility.validateToken(jwt))
		{
		List<Label> labels=getLabels(updatedto.getLabel(),jwt);
		List<Images> images=getImages(updatedto.getImages(),updatedto.getId()); 
		Notes note=repository.getNotes(updatedto.getId());
		if(note==null) throw new NoteNotFoundException("Note not available for this id");
		System.out.println(note);
		Notes notes=utility.getUpdatedNote(updatedto, note, images, labels);
		repository.save(notes);
		return true;
		}
		else
			throw new JWTTokenException("Problem with your Token");
	}
	
	
}

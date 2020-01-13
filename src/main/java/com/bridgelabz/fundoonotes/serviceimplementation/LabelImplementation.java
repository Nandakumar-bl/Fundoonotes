package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.exceptions.LabelAlreadyExsistException;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class LabelImplementation implements LabelService {

	@Autowired
	LabelRepository repository;
	@Autowired
	Utility utility;

	@Override
	public String createLabel(LabelDTO labeldto, String jwt) throws LabelAlreadyExsistException {

		UserInfo user = utility.getUser(jwt);

		if (repository.getLabel(labeldto.getLabel()) == null) {
			repository.createLabel(labeldto.getLabel(), user);
			return "success";
		} else {
			throw new LabelAlreadyExsistException("Label already exsist");
		}
	}

	@Override
	public String updateLabel(LabelDTO labeldto) throws LabelAlreadyExsistException 
	{

		if (repository.getLabel(labeldto.getLabel()) == null) 
		{
			if (repository.updateLabel(labeldto.getLabel(), labeldto.getId()) != 0)
				return "success";
			else
				return "failed";
		}
		else
		{
			throw new LabelAlreadyExsistException("Label already exsist");
		}

	}

	@Override
	public boolean deletelabel(int id) throws LabelNotFoundException
	{
		if (repository.getLabelbyId(id) != null) 
		{

		if(repository.deleteLabel(id)!=0)
			    return true;
			else 
				return false;
	
		}
		else
			throw new LabelNotFoundException("No Label Found to delete");
			
	}
	
	public Label getLabel(int id)
	{
		return repository.getLabel(id);
	}
	
	
	
	public List<Label> getAllUserLabels(String jwt)
	{
		UserInfo user=utility.getUser(jwt);
		return repository.getAllUserLabel(user.getId());
	}
	
	public List<Label> findbynoteid(int id)
	{
		Notes note=repository.findbynoteid(id);

		return 	note.getLabel();
	}

}

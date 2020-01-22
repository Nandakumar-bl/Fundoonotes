package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.List; 
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.exceptions.LabelAlreadyExsistException;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class LabelImplementation implements LabelService {

	@Autowired
	private LabelRepository repository;
	@Autowired
	private Utility utility;

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
	
	public LabelDTO getLabel(int id)
	{
		Labels label=repository.getLabel(id);
		LabelDTO labeldto=new LabelDTO();
		BeanUtils.copyProperties(label, labeldto);
		return labeldto;
	}
	
	
	
	public List<LabelDTO> getAllUserLabels(String jwt)
	{
		UserInfo user=utility.getUser(jwt);
		List<Labels> labels=repository.getAllUserLabel(user.getId());
		List<LabelDTO> labeldtouser=labels.stream().map(s->
				{
					LabelDTO temp=new LabelDTO();
					BeanUtils.copyProperties(s,temp);
					return temp;
				}).collect(Collectors.toList());
		return labeldtouser;
	}
	
	public List<LabelDTO> findbynoteid(int id)
	{
		Notes note=repository.findbynoteid(id);
		List<Labels> labels=note.getLabels();
		List<LabelDTO> noteslabel=labels.stream().map(s->
		{
			LabelDTO temp=new LabelDTO();
			BeanUtils.copyProperties(s,temp);
			return temp;
		}).collect(Collectors.toList());
      return noteslabel;
	}

}

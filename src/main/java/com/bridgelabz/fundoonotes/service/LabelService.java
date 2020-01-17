package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDTO; 
import com.bridgelabz.fundoonotes.exceptions.LabelAlreadyExsistException;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.model.Label;

public interface LabelService 
{
	public String createLabel(LabelDTO labeldto,String jwt) throws LabelAlreadyExsistException;
	public String updateLabel(LabelDTO labeldto) throws LabelAlreadyExsistException;
	public boolean deletelabel(int id) throws LabelNotFoundException;
	public LabelDTO getLabel(int id);
	public List<LabelDTO> getAllUserLabels(String jwt);
	public List<LabelDTO> findbynoteid(int id);
}

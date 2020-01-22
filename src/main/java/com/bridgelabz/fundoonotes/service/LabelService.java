package com.bridgelabz.fundoonotes.service;

import java.util.List; 

import com.bridgelabz.fundoonotes.dto.LabelDTO; 
import com.bridgelabz.fundoonotes.exceptions.LabelAlreadyExsistException;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;

public interface LabelService 
{
	String createLabel(LabelDTO labeldto,String jwt) throws LabelAlreadyExsistException;
	String updateLabel(LabelDTO labeldto) throws LabelAlreadyExsistException;
	boolean deletelabel(int id) throws LabelNotFoundException;
	LabelDTO getLabel(int id);
	List<LabelDTO> getAllUserLabels(String jwt);
	List<LabelDTO> findbynoteid(int id);
}

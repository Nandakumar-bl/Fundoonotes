package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDTO; 
import com.bridgelabz.fundoonotes.exceptions.LabelAlreadyExsistException;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;

public interface LabelService 
{
	public String createLabel(LabelDTO labeldto,String jwt) throws LabelAlreadyExsistException;
	public String updateLabel(LabelDTO labeldto) throws LabelAlreadyExsistException;
	public boolean deletelabel(int id) throws LabelNotFoundException;
}

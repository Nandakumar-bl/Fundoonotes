package com.bridgelabz.fundoonotes.utility;

import java.util.Comparator;

import com.bridgelabz.fundoonotes.dto.NoteDTO;

public class SortbyID implements Comparator<NoteDTO>
{

	@Override
	public int compare(NoteDTO o1, NoteDTO o2) {
	
		return o2.getId()-o1.getId();
	}
	
	
	

}

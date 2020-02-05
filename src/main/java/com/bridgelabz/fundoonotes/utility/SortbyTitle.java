package com.bridgelabz.fundoonotes.utility;

import java.util.Comparator;

import com.bridgelabz.fundoonotes.dto.NoteDTO;

public class SortbyTitle implements Comparator<NoteDTO>
{

	@Override
	public int compare(NoteDTO o1, NoteDTO o2) {
	
		int result=o2.getTitle().compareTo(o1.getTitle());
		return result;
	}
	
	
	

}

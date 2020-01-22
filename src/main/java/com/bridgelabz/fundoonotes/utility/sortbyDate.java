package com.bridgelabz.fundoonotes.utility;

import java.util.Comparator;

import com.bridgelabz.fundoonotes.dto.NoteDTO;

public class sortbyDate implements Comparator<NoteDTO> {

	public int compare(NoteDTO o1, NoteDTO o2) {

		/*
		 * System.out.println(o1.getCreatedTime());
		 * System.out.println(o2.getCreatedTime()); int result =
		 * o1.getCreatedTime().compareTo(o2.getCreatedTime()); return -result;
		 */
		return 0;
	}
}

package com.bridgelabz.fundoonotes.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Label {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String label;
	/*
	 * @ManyToMany(mappedBy = "label") private List<Notes> notes;
	 */
	
	@JsonIgnore
	  @ManyToOne 
	  private UserInfo userinfo;
	  
	 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	@Override
	public String toString() {
		return "Label [id=" + id + ", label=" + label + "]";
	}
	
	

}

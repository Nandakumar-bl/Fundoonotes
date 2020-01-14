package com.bridgelabz.fundoonotes.model;

import java.util.List; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
public class Collaborator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String collaborator;

	@JsonIgnore
	@ManyToOne
	private Notes notes;

	public Collaborator() {}
	
	public Collaborator(String collaborator, Notes notes) {
		this.collaborator = collaborator;
		this.notes = notes;
	}
	
	@JsonIgnore
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Collaborator [id=" + id + ", collaborator=" + collaborator + ", notes=" + notes + "]";
	}


	
}

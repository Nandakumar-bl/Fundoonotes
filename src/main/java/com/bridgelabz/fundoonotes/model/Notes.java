package com.bridgelabz.fundoonotes.model;

import java.util.Date;
import java.util.List; 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String takeanote;
	@Column(columnDefinition = "boolean default false")
	private boolean isarchieve;
	@Column(columnDefinition = "boolean default false")
	private boolean istrash;
	@Column(columnDefinition = "boolean default false")
	private boolean ispinned;
	private String reminder;
    private String color;
    @LastModifiedDate
    private Date lastupdate;
	@ManyToMany
	private List<Label> label;
	@OneToMany(mappedBy = "notes")
	private List<Images> images;
	@Column(columnDefinition = "timestamp default current_timestamp")
	private Date CreatedTime;
	@JsonIgnore
	@OneToOne
	private UserInfo userinfo;
	@OneToMany(mappedBy = "notes")
	private List<Collaborator> collaborators;
	
	
	public Notes() {}
	

	public Notes(int id,String title, String takeanote,String reminder, String color, List<Label> label, List<Images> images) 
	{
		this.id=id;
		this.title = title;
		this.takeanote = takeanote;
		this.reminder = reminder;
		this.color = color;
		this.label = label;
		this.images = images;
		
	}
	
	public Notes(String title, String takeanote,String reminder, String color, List<Label> label, List<Images> images,UserInfo userinfo) 
	{
		this.title = title;
		this.takeanote = takeanote;
		this.reminder = reminder;
		this.color = color;
		this.label = label;
		this.images = images;
		this.userinfo=userinfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTakeanote() {
		return takeanote;
	}

	public void setTakeanote(String takeanote) {
		this.takeanote = takeanote;
	}




	public boolean isIsarchieve() {
		return isarchieve;
	}

	public void setIsarchieve(boolean isarchieve) {
		this.isarchieve = isarchieve;
	}

	public boolean isIstrash() {
		return istrash;
	}

	public void setIstrash(boolean istrash) {
		this.istrash = istrash;
	}

	public boolean isIspinned() {
		return ispinned;
	}

	public void setIspinned(boolean ispinned) {
		this.ispinned = ispinned;
	}

	
	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public Date getCreatedTime() {
		return CreatedTime;
	}

	public void setCreatedTime(Date createdTime) {
		CreatedTime = createdTime;
	}

	@JsonIgnore
	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}


	public Date getLastupdate() {
		return lastupdate;
	}


	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}


	public UserInfo getUserinfo() {
		return userinfo;
	}


	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}


	
	public List<Collaborator> getCollaborators() {
		return collaborators;
	}


	public void setCollaborators(List<Collaborator> collaborators) {
		this.collaborators = collaborators;
	}


	@JsonIgnore
	public List<Images> getImages() {
		return images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", takeanote=" + takeanote + ", isarchieve=" + isarchieve
				+ ", istrash=" + istrash + ", ispinned=" + ispinned + ", reminder=" + reminder + ", color=" + color
				+ ", label=" + label + ", images=" + images + ", CreatedTime=" + CreatedTime + ", userinfo=S]";
	}

	



	
	
	}


	


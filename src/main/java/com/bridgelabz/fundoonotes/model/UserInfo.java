package com.bridgelabz.fundoonotes.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class UserInfo 
{
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String username;
	@NotNull
	private String Firstname;
	@NotNull
	private String Lastname;
	@NotNull
	@Pattern(regexp = "	^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",message = "Enter a valid email")
	private String email;
	@NotNull
	@Column(columnDefinition = "boolean default false")
	private boolean isemailverified;
	@NotNull
	private String password;
	@NotNull
	@Column(columnDefinition = "timestamp default current_timestamp")
	private Date createddate;
	@OneToMany(mappedBy = "userinfo")
	private List<Notes> notes;
	@OneToMany(mappedBy = "userinfo")
	private List<Label> labels;
		
	
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getIsemailverified() {
		return isemailverified;
	}
	public void setIsemailverified(boolean isemailverified) {
		this.isemailverified = isemailverified;
	}
	
	public List<Notes> getNotes() {
		return notes;
	}
	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", Firstname=" + Firstname + ", Lastname=" + Lastname
				+ ", email=" + email + ", password=" + password + "]";
	}

	
}

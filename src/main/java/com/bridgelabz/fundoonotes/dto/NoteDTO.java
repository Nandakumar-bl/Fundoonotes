package com.bridgelabz.fundoonotes.dto;

import java.util.List; 

public class NoteDTO 
{
	private String title;
	private String takeanote;
	private boolean isarchieve;
	private boolean ispinned;
	private String color; 
	private String reminder;
	private List<String> label;
	private List<String> drawing;
	private List<String> images;
	private List<String> collabarotor;
	
	
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
	public boolean isIspinned() {
		return ispinned;
	}
	public void setIspinned(boolean ispinned) {
		this.ispinned = ispinned;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	public List<String> getLabel() {
		return label;
	}
	public void setLabel(List<String> label) {
		this.label = label;
	}
	public List<String> getDrawing() {
		return drawing;
	}
	public void setDrawing(List<String> drawing) {
		this.drawing = drawing;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public List<String> getCollabarotor() {
		return collabarotor;
	}
	public void setCollabarotor(List<String> collabarotor) {
		this.collabarotor = collabarotor;
	}
	@Override
	public String toString() {
		return "NoteDTO [title=" + title + ", takeanote=" + takeanote + ", isarchieve=" + isarchieve + ", ispinned="
				+ ispinned + ", color=" + color + ", label=" + label + ", drawing=" + drawing + ", images=" + images
				+ ", collabarotor=" + collabarotor + "]";
	}
	
	
	


}

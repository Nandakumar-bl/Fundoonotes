package com.bridgelabz.fundoonotes.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

public class ErrorResponse 
{
	private int Errorcode;
	private String message;
	private Object details;
	public ErrorResponse(int errorcode, String message, Object details) {
		super();
		Errorcode = errorcode;
		this.message = message;
		this.details = details;
	}
	public int getErrorcode() {
		return Errorcode;
	}
	public void setErrorcode(int errorcode) {
		Errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getDetails() {
		return details;
	}
	public void setDetails(Object details) {
		this.details = details;
	}
	


}

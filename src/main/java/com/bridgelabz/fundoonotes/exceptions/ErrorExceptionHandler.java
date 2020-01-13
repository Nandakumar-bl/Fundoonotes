package com.bridgelabz.fundoonotes.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.ErrorResponse;
import com.bridgelabz.fundoonotes.response.JWTTokenException;

@RestControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler
{
	
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorResponse> recordNotFound(LoginException ex)
	{
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400,"User Credential issues",ex.getMessage()));
		
	}
	
	@ExceptionHandler(JWTTokenException.class)
	public ResponseEntity<ErrorResponse> tokenError(JWTTokenException ex)
	{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(400,"Problem With Token",ex.getMessage()));
		
	}
	
	@ExceptionHandler(MailIDNotFoundException.class)
	public ResponseEntity<ErrorResponse> mailId(MailIDNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(400,"Issue with mail id",ex.getMessage()));
		
	}
	
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<ErrorResponse> noteNotFound(NoteNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(400,"Issue in Notes",ex.getMessage()));
		
	}
	
	@ExceptionHandler(UpdatingNoteException.class)
	public ResponseEntity<ErrorResponse> updatingNote(UpdatingNoteException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(400,"Issue in Notes updating",ex.getMessage()));
		
	}
	
	@ExceptionHandler(LabelAlreadyExsistException.class)
	public ResponseEntity<ErrorResponse> labelFound(LabelAlreadyExsistException ex)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Label Error",ex.getMessage()));
	}
	
	@ExceptionHandler(LabelUpdatingException.class)
	public ResponseEntity<ErrorResponse> updateLabel(LabelUpdatingException ex)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Label Error",ex.getMessage()));
	}
	
	@ExceptionHandler(LabelNotFoundException.class)
	public ResponseEntity<ErrorResponse> updateLabel(LabelNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Label Error",ex.getMessage()));
	}
	
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> userException(UserException ex)
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"User Exception",ex.getMessage()));
	}
	
	
	

}

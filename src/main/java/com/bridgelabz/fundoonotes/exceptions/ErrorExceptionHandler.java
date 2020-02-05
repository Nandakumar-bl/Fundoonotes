package com.bridgelabz.fundoonotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bridgelabz.fundoonotes.response.ErrorResponse;
import com.bridgelabz.fundoonotes.response.JWTTokenException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler
{
	
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ErrorResponse> recordNotFound(LoginException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400,"User Credential issues",ex.getMessage()));
		
	}
	
	@ExceptionHandler(JWTTokenException.class)
	public ResponseEntity<ErrorResponse> tokenError(JWTTokenException ex)
	{
		log.error(ex.getMessage());
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
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(400,"Issue in Notes",ex.getMessage()));
		
	}
	
	@ExceptionHandler(UpdatingNoteException.class)
	public ResponseEntity<ErrorResponse> updatingNote(UpdatingNoteException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(400,"Issue in Notes updating",ex.getMessage()));
		
	}
	
	@ExceptionHandler(LabelAlreadyExsistException.class)
	public ResponseEntity<ErrorResponse> labelFound(LabelAlreadyExsistException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Label found",ex.getMessage()));
	}
	
	@ExceptionHandler(LabelUpdatingException.class)
	public ResponseEntity<ErrorResponse> updateLabel(LabelUpdatingException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Label Error",ex.getMessage()));
	}
	
	@ExceptionHandler(LabelNotFoundException.class)
	public ResponseEntity<ErrorResponse> updateLabel(LabelNotFoundException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Label not found",ex.getMessage()));
	}
	
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> userException(UserException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"User Exception",ex.getMessage()));
	}
	
	@ExceptionHandler(EmailAlreadyExsist.class)
	public ResponseEntity<ErrorResponse> emailAlreadyExsist(EmailAlreadyExsist ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Mail id Exception",ex.getMessage()));
	}
	
	
	@ExceptionHandler(CollaboratorNotFoundException.class)
	public ResponseEntity<ErrorResponse> collaboratorException(CollaboratorNotFoundException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Mail id Exception",ex.getMessage()));
	}
	
	@ExceptionHandler(FileFormatException.class)
	public ResponseEntity<ErrorResponse> fileFormatException(FileFormatException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Format Exception",ex.getMessage()));
	}
	@ExceptionHandler(NoProfileFoundException.class)
	public ResponseEntity<ErrorResponse> noProfileFoundException(NoProfileFoundException ex)
	{
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(400,"Profile not found",ex.getMessage()));
	}
	
	
	

}

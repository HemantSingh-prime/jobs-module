package com.ps.js.exception;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ps.js.response.ErrorResponse;
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class JobDetailsExceptionalHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(JobDetailsNotCreatedException.class)
	public ResponseEntity<Object> exception(JobDetailsNotCreatedException exception) {
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("Internal Sever error Job details not added");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(JobDetailsNotFoundException.class)
	public ResponseEntity<Object> exception(JobDetailsNotFoundException exception){
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());
		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("Job Details Not found exception");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Object> exception(MethodArgumentNotValidException exception){
//		List<String> details = new ArrayList<String>();
//		details.add(HttpStatus.BAD_REQUEST.toString());
//		ErrorResponse response=new ErrorResponse();
//		if (exception.getMessage() == null)
//			response.setMessage("Validation failed exception");
//		details.add(exception.getLocalizedMessage());
//		response.setDetails(details);;
//		return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
//	}
	
	@Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    List<String> details = new ArrayList();
	    for(ObjectError error : ex.getBindingResult().getAllErrors()) {
	      details.add(error.getDefaultMessage());
	    }
	    ErrorResponse response = new ErrorResponse();
	    response.setDetails(details);
	    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	  }
	
	@ExceptionHandler(LocationsNotFoundException.class)
	public ResponseEntity<Object> exception(LocationsNotFoundException exception){
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());
		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("Location Not found exception");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SkillNotFoundException.class)
	public ResponseEntity<Object> exception(SkillNotFoundException exception){
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());
		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("Skill Not found exception");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}
}

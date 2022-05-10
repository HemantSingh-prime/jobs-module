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

/**
 * 
 * @author Hemant
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class JobDetailsExceptionalHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(JobDetailsNotCreatedException.class)
	public ResponseEntity<Object> exception(JobDetailsNotCreatedException exception) {
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage(ErrorMessages.INTERNAL_SERVER_ERROR_JOB_DETAILS_NOT_CREATED_EXCEPTION.toString());
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
			response.setMessage(ErrorMessages.JOB_DETAILS_NOT_FOUND_EXCEPTION.toString());
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}

	
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
			response.setMessage(ErrorMessages.LOCATION_NOT__FOUND_EXCEPTION.toString());
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
			response.setMessage(ErrorMessages.SKILL_NOT_FOUND_EXCEPTION.toString());
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(RolesAndResponsebilityNotFoundException.class)
	public ResponseEntity<Object> exception(RolesAndResponsebilityNotFoundException exception){
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());
		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage(ErrorMessages.ROLES_AND_RESPONSEBILITY_NOT_FOUND.toString());
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response,HttpStatus.NOT_FOUND);
	}
	
}

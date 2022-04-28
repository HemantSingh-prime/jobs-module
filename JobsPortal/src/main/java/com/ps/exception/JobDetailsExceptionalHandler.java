package com.ps.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ps.response.ErrorResponse;

@ControllerAdvice
public class JobDetailsExceptionalHandler {

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
}

package com.ps.js.exception;

public class JobDetailsNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobDetailsNotFoundException() {
		
	}
	
	public JobDetailsNotFoundException(String message){
		super(message);
	}
}

package com.ps.js.exception;

public class JobDetailsNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobDetailsNotCreatedException() {
		super();
	}
	
	public JobDetailsNotCreatedException(String message) {
		super(message);
	}
}

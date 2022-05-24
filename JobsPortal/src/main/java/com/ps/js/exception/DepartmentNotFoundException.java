package com.ps.js.exception;

public class DepartmentNotFoundException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentNotFoundException() {
		
	}
	
public DepartmentNotFoundException(String message) {
		super(message);
	}
}

package com.ps.js.exception;

public class SkillNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SkillNotFoundException() {
		
	}
	
public SkillNotFoundException(String message) {
	 super(message);	
	}
}

package com.ps.js.exception;

public class LocationsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LocationsNotFoundException() {
		super();
	}
   
	public LocationsNotFoundException(String message) {
		super(message);
	}
}

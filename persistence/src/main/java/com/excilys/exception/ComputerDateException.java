package com.excilys.exception;

public class ComputerDateException extends ComputerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
	public ComputerDateException() {
		message="Introduced must be before discontinued !";
	}
	
	
	@Override
	public String getMessage() {
		return message;
	}
}

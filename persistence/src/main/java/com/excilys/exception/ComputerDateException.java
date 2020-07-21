package com.excilys.exception;

public class ComputerDateException extends ComputerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public ComputerDateException() {
		message="Introduced must be before discontinued !";
	}
	
	public String getMessage() {
		return message;
	}
}

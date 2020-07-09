package com.excilys.formation.CDB.exceptions;

public class ComputerNameException extends ComputerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public ComputerNameException() {
		message="Computer name can't be empty !";
	}
	
	public String getMessage() {
		return message;
	}

	

}

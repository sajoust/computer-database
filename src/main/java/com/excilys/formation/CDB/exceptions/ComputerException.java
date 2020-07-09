package com.excilys.formation.CDB.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.CDB.connection.ConnectionHikari;

public class ComputerException extends Exception {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ConnectionHikari.class);

	public ComputerException() {
		
	}
}

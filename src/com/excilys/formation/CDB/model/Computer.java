package com.excilys.formation.CDB.model;

import java.time.LocalDateTime;

public class Computer {

	private long id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private long companyID;
	
	
	public Computer(long id, String name, LocalDateTime introduced, LocalDateTime discontinued, long companyID) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
	}
	
	public Computer(String name) {
		super();
		this.name = name;

	}
	
	
	
}


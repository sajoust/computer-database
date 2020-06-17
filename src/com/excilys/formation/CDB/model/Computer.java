package com.excilys.formation.CDB.model;

import java.sql.Date;

public class Computer {

	private Long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Long companyID;
	
	public Computer() {
		
	}
	public Computer(String name, Date introduced, Date discontinued, Long companyID) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
	}
	
	
	public Computer(Long id, String name, Date introduced, Date discontinued, Long companyID) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}


	public Date getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}


	public Long getCompanyID() {
		return companyID;
	}


	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}


	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyID=" + companyID + "]";
	}



	
	
	
	

	
	
	
}


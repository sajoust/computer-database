package com.excilys.formation.CDB.DTO;

import com.excilys.formation.CDB.model.Company;

public class DTOCompany {
	private String id;
	private String name;
	private Company company;
	
	
	public DTOCompany(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public DTOCompany(String id) {
		this.id=id;
	}
	
	public DTOCompany(Company company) {
		this.setCompany(company);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
	
}

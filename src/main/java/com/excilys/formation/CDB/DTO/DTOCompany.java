package com.excilys.formation.CDB.DTO;


public class DTOCompany {
	private String id;
	private String name;

	
	
	public DTOCompany(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public DTOCompany(String id) {
		this.id=id;
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

	
	
	
	
}

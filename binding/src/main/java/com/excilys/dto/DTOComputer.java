package com.excilys.dto;

public class DTOComputer {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private DTOCompany dtoCompany;
	
	
	
	public DTOComputer(String name, String introduced, String discontinued, DTOCompany dtoCompany) {

		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.dtoCompany = dtoCompany;
	}
	
	public DTOComputer(String id, String name, String introduced, String discontinued, DTOCompany dtoCompany) {

		this.id=id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.dtoCompany = dtoCompany;
	}
	
	public DTOComputer() {
		
	}

	public boolean validateData() {
		return true;
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}



	public DTOCompany getDtoCompany() {
		return dtoCompany;
	}

	public void setDtoCompany(DTOCompany dtoCompany) {
		this.dtoCompany = dtoCompany;
	}

	@Override
	public String toString() {
		return "DTOComputer [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", dtoCompany=" + dtoCompany + "]";
	}


	
	
	
	
	
	
}

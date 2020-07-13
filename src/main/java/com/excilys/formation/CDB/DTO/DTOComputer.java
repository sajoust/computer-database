package com.excilys.formation.CDB.DTO;

public class DTOComputer {
	private String id;
	private String computerName;
	private String introduced;
	private String discontinued;
	private DTOCompany dtoCompany;
	
	
	
	public DTOComputer(String computerName, String introduced, String discontinued, DTOCompany dtoCompany) {

		this.computerName = computerName;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.dtoCompany = dtoCompany;
	}
	
	public DTOComputer(String id, String computerName, String introduced, String discontinued, DTOCompany dtoCompany) {

		this.id=id;
		this.computerName = computerName;
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
		return computerName;
	}

	public void setName(String name) {
		this.computerName = name;
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
		return "DTOComputer [name=" + computerName + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", dtoCompany=" + dtoCompany + "]";
	}


	
	
	
	
	
	
}

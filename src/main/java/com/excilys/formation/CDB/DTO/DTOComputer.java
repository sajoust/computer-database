package com.excilys.formation.CDB.DTO;

public class DTOComputer {
	//private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyID;
	private DTOCompany dtoCompany;
	
	
	
	public DTOComputer(String name, String introduced, String discontinued, String companyID) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
	}

	public boolean validateData() {
		return true;
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	@Override
	public String toString() {
		return "DTOComputer [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyID=" + companyID + "]";
	}

	
	
	
	
	
	
}

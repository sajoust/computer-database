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

	public DTOComputer(Builder builder) {
		
		this.id=builder.idBuild;
		this.name=builder.nameBuild;
		this.introduced=builder.introducedBuild;
		this.discontinued=builder.discontinuedBuild;
		this.dtoCompany=builder.dtoCompanyBuild;
	
	
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


	
	public static class Builder{
		
		private String idBuild;
		private String nameBuild;
		private String introducedBuild;
		private String discontinuedBuild;
		private DTOCompany dtoCompanyBuild;
		
		public Builder setIdBuild(String id) {
			this.idBuild = id;
			return this;
		}
		
		public Builder setNameBuild(String name) {
			this.nameBuild=name;
			return this;
		}
		
		public Builder setIntroducedBuild(String introduced) {
			this.introducedBuild = introduced;
			return this;
		}
		
		public Builder setDiscontinuedBuild(String dicontinued) {
			this.discontinuedBuild = dicontinued;
			return this;
		}
		
		public Builder setDtoCompanyBuild(DTOCompany dtoCompany) {
			this.dtoCompanyBuild = dtoCompany;
			return this;
		}
		
		public DTOComputer build() {
			return new DTOComputer(this);
		}
		
	}
	
	
	
	
}

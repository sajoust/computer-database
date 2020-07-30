package com.excilys.dto;

public class DTOCompany {
	private String id;
	private String name;

	public DTOCompany(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public DTOCompany(String id) {
		this.id = id;
	}

	public DTOCompany(Builder builder) {

		this.id = builder.idBuild;
		this.name = builder.nameBuild;

	}
	public DTOCompany() {
		
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

	public static class Builder {

		private String idBuild;
		private String nameBuild;

		public Builder setIdBuild(String id) {
			this.idBuild = id;
			return this;
		}

		public Builder setNameBuild(String name) {
			this.nameBuild = name;
			return this;
		}

		public DTOCompany build() {
			return new DTOCompany(this);
		}

	}

}

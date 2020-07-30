package com.excilys.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.DTOCompany;
import com.excilys.model.Company;



public class CompanyDTOMapper {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CompanyDTOMapper.class);

	private CompanyDTOMapper() {
		throw new AssertionError();
	}

	public static DTOCompany companyToDto(Company company) {

		String id = String.valueOf(company.getId());
		String name = company.getName();
		

		return new DTOCompany(id, name);
	}

}

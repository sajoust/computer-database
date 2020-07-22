package com.excilys.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.DTOCompany;
import com.excilys.model.Company;



public class CompanyDTOMapper {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CompanyDTOMapper.class);


	public static DTOCompany CompanyToDTO(Company company) {

		String id = String.valueOf(company.getId());
		String name = company.getName();
		DTOCompany DTO = new DTOCompany(id, name);

		return DTO;
	}

}

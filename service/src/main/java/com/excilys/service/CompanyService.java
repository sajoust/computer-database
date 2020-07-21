package com.excilys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.PageDTO;
import com.excilys.formation.CDB.mapper.CompanyDTOMapper;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Page;
import com.excilys.formation.CDB.persistence.CompanyDAO;


@Service
public class CompanyService {
	

	private CompanyDAO companyDAO;

	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO=companyDAO;
	}
	
	public List<DTOCompany> getAll(PageDTO pageDto) {
		
		
		List<Company> companyList = companyDAO.getAll(pageDto);
		List<DTOCompany> dtoCompanyList=new ArrayList<DTOCompany>();
		
		for (Company company : companyList) {
			dtoCompanyList.add(CompanyDTOMapper.CompanyToDTO(company));
		}
		
		return dtoCompanyList;
	}
	
	public String get(String id) {
				
		return companyDAO.get(id).toString();
		
	}
	public void delete(String id) {
		companyDAO.delete(id);
	}
	

	

}

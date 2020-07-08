package com.excilys.formation.CDB.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.mapper.CompanyDTOMapper;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.persistence.CompanyDAO;


@Service
public class CompanyService {
	

	private CompanyDAO companyDAO;

	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO=companyDAO;
	}
	
	public List<DTOCompany> getAll(int nbLines, int pageEnCours, String filter, String order) {
		
		
		List<Company> companyList = companyDAO.getAll(nbLines, pageEnCours, filter, order);
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

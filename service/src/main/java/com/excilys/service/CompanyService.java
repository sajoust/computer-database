package com.excilys.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.excilys.dao.CompanyDAO;
import com.excilys.dto.DTOCompany;
import com.excilys.dto.PageDTO;
import com.excilys.mapper.CompanyDTOMapper;
import com.excilys.mapper.PageMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;


@Service
public class CompanyService {
	

	private CompanyDAO companyDAO;

	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO=companyDAO;
	}
	
	public List<DTOCompany> getAll(PageDTO pageDto) {
		
		Page currentPage = PageMapper.dtoToPage(pageDto);
		List<Company> companyList = companyDAO.getAll(currentPage);
		List<DTOCompany> dtoCompanyList=new ArrayList<DTOCompany>();
		
		for (Company company : companyList) {
			dtoCompanyList.add(CompanyDTOMapper.CompanyToDTO(company));
		}
		
		return dtoCompanyList;
	}
	
	public String get(String id) {
				
		return companyDAO.get(id).toString();
		
	}
	public void delete(String id) throws PersistenceException, SQLException {
		companyDAO.delete(id);
	}
	

	

}

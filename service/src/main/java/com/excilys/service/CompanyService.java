package com.excilys.service;

import java.util.ArrayList;
import java.util.List;

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
		List<DTOCompany> dtoCompanyList=new ArrayList<>();
		
		for (Company company : companyList) {
			dtoCompanyList.add(CompanyDTOMapper.companyToDto(company));
		}
		
		return dtoCompanyList;
	}
	
	public DTOCompany get(String id) {
				
		return CompanyDTOMapper.companyToDto(companyDAO.get(id));
		
	}
	public void delete(String id) {
		companyDAO.delete(id);
	}
	

	

}

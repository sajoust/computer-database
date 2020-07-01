package com.excilys.formation.CDB.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.mapper.CompanyMapper;
import com.excilys.formation.CDB.mapper.ComputerMapper;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;
import com.excilys.formation.CDB.persistence.CompanyDAO;



public class CompanyService {
private static CompanyDAO companyDAO;
	
	
	//prend un company et renvoie string
	
	private CompanyService() {
		companyDAO = new CompanyDAO();
	}
	
	
	private static class CompanyServiceHolder {
		private final static CompanyService instance = new CompanyService();
	}
	
	public static CompanyService getInstance() {
		return CompanyServiceHolder.instance;
	}
	

	public List<DTOCompany> getAll(int nbLines, int pageEnCours, String filter) {
		ResultSet resultSet = companyDAO.getAll(nbLines, pageEnCours, filter);
		List<Company> companyList = new ArrayList<>();
		List<DTOCompany> dtoCompanyList=new ArrayList<DTOCompany>();
		
		try {
			while (resultSet.next()) {
				companyList.add(CompanyMapper.processResults(resultSet));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (Company company : companyList) {
			dtoCompanyList.add(CompanyMapper.CompanyToDTO(company));
		}

		
		
		return dtoCompanyList;
	}
	public List<DTOCompany> getAll() {
		List<Company> companyList = companyDAO.getAll();
		List<DTOCompany> DTOList=new ArrayList<DTOCompany>();
		for (Company company : companyList) {
			DTOList.add(CompanyMapper.CompanyToDTO(company));
		}

		
		
		return DTOList;
	}
	
	public String get(String id) {
				
		return companyDAO.get(id).toString();
		
	}
	

	

}

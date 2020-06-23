package com.excilys.formation.CDB.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.persistence.CompanyDAO;


public class CompanyService {
private static CompanyDAO companyDAO;
	
	
	//prend un company et renvoie string
	
	public CompanyService() {
		companyDAO = new CompanyDAO();
	}
	

	public List<String> getAll(int nbLines, int pageEnCours) {
		List<String> strList = new ArrayList<String>();
		List<Company> companyList = companyDAO.getAll(nbLines,pageEnCours);
		for (Company company : companyList) {
			strList.add(company.toString());
		}
		return strList;
	}
	
	public String get(String id) {
				
		return companyDAO.get(id).toString();
		
	}
	

	

}

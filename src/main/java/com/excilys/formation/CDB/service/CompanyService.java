package com.excilys.formation.CDB.service;

import java.util.ArrayList;

import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.persistence.CompanyDAO;


public class CompanyService {
private static CompanyDAO companyDAO;
	
	
	//prend un company et renvoie string
	
	public CompanyService() {
		companyDAO = new CompanyDAO();
	}
	

	public ArrayList<String> getAll(int nbLines, int pageEnCours) {
		ArrayList<String> strList = new ArrayList<String>();
		ArrayList<Company> companyList = companyDAO.getAll(nbLines,pageEnCours);
		for (Company company : companyList) {
			strList.add(company.toString());
		}
		return strList;
	}
	
	public String get(String id) {
				
		return companyDAO.get(id).toString();
		
	}
	

	

}

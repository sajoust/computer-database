package com.excilys.formation.CDB.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.model.Company;

public class CompanyMapper {

	public static Company processResults(ResultSet resultSet) {

		try {
			long ID = resultSet.getLong(1);
			String name = resultSet.getString("name");

			Company c = new Company(ID, name);
			return c;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public static DTOCompany CompanyToDTO(Company company) {
		
		String id = String.valueOf(company.getId());
		String name = company.getName();
				
		DTOCompany DTO = new DTOCompany(id,name);
		return DTO;
	}

	public static  int countResults(ResultSet resultSet) {
		try {
			return resultSet.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}



}

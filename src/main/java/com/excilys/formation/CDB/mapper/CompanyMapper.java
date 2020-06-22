package com.excilys.formation.CDB.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;


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
	
}

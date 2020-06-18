package com.excilys.formation.CDB.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.CDB.model.Computer;

public class ComputerMapper {

	
	public Computer processResults(ResultSet resultSet) {
		
		try {
				long ID = resultSet.getLong(1);
				String name = resultSet.getString("name");
				Date ldIntroduced = resultSet.getDate("introduced");
				Date ldDiscontinued = resultSet.getDate("discontinued");
				Long company_ID = resultSet.getLong("company_id");

				Computer c = new Computer(ID, name, ldIntroduced, ldDiscontinued, company_ID);
				return c;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	
	public int countResults(ResultSet resultSet) {
		try {
			return resultSet.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
}

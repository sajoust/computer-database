package com.excilys.formation.CDB.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.CDB.model.Company;

public class CompanyDAOMapper implements RowMapper<Company> {

	private static Logger logger = LoggerFactory.getLogger(CompanyDTOMapper.class);
	
	
	public static Company resultSetToCompany(ResultSet resultSet) {

		
		try {
			long ID = resultSet.getLong(1);
			String name = resultSet.getString("name");
			Company c = new Company(ID, name);
			return c;

		} catch (SQLException e) {
			logger.error("probleme dans process results");
			e.printStackTrace();
		}
		
		return null;

	}
	
	public static int countResults(ResultSet resultSet) {
		try {
			return resultSet.getInt(1);
		} catch (SQLException e) {
			logger.error("result set in countResults");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return resultSetToCompany(rs);
	}
	

}

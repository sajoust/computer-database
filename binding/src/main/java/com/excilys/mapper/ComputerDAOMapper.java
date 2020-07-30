package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.dto.DTOComputer;
import com.excilys.model.Company;
import com.excilys.model.Computer;



public class ComputerDAOMapper implements RowMapper<Computer> {

	private static Logger logger = LoggerFactory.getLogger(ComputerDAOMapper.class);	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
	
	
	public static Computer resultSetToComputer(ResultSet resultSet) {
	
		try {
	
				Long id = resultSet.getLong(1);
				String name = resultSet.getString("name");
				LocalDate ldIntroduced = (resultSet.getString("introduced") == null) ? null
						: LocalDate.parse(resultSet.getString("introduced"), dateFormatter);
				LocalDate ldDiscontinued = (resultSet.getString("discontinued") == null) ? null
						: LocalDate.parse(resultSet.getString("discontinued"), dateFormatter);
				String companyName = resultSet.getString("company_name");
				Long companyID = resultSet.getLong("company_id");
				Company company = new Company(companyID, companyName);
				
				return new Computer(id, name, ldIntroduced, ldDiscontinued, company);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Computer dtoToComputer(DTOComputer dtoComputer) {
		
		
		
		String name = dtoComputer.getName();
		LocalDate introduced = (dtoComputer.getIntroduced()==null?null:LocalDate.parse(dtoComputer.getIntroduced()));
		LocalDate discontinued = (dtoComputer.getDiscontinued()==null?null:LocalDate.parse(dtoComputer.getDiscontinued()));
		Long companyId = Long.parseLong(dtoComputer.getDtoCompany().getId());
		String companyName = dtoComputer.getDtoCompany().getName();
		Company company = new Company(companyId, companyName);
		if (dtoComputer.getId()!=null) {
			Long id = Long.parseLong(dtoComputer.getId());
			return new Computer(id, name, introduced, discontinued, company);
		}
		return new Computer(name, introduced, discontinued, company);
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
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return resultSetToComputer(rs);
	}
}

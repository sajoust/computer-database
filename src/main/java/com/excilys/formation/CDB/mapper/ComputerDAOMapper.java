package com.excilys.formation.CDB.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;

public class ComputerDAOMapper {

	private static Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);
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
				Computer c = new Computer(id, name, ldIntroduced, ldDiscontinued, company);
				return c;
			
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
}

package com.excilys.formation.CDB.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;

public class ComputerMapper {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

	public static Computer processResults(ResultSet resultSet) {

		try {
			Long id = resultSet.getLong(1);
			String name = resultSet.getString("name");
			LocalDate ldIntroduced = (resultSet.getString("introduced") == null) ? null
					: LocalDate.parse(resultSet.getString("introduced"), dateFormatter);
			LocalDate ldDiscontinued = (resultSet.getString("discontinued")== null)? null:
					LocalDate.parse(resultSet.getString("discontinued"), dateFormatter);
			String companyName = resultSet.getString("company_name");
			Long companyID = resultSet.getLong("company_id");
			Company company = new Company(companyID,companyName);

			Computer c = new Computer(id, name, ldIntroduced, ldDiscontinued, company);
			return c;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public static DTOComputer ComputerToDTO(Computer computer) {
		String id = String.valueOf(computer.getId());
		String name = computer.getName();
		String introduced = computer.getIntroduced()==null?"":computer.getIntroduced().toString();
		String discontinued = computer.getDiscontinued()==null?"":computer.getDiscontinued().toString();
		DTOCompany dtoCompany = new DTOCompany(String.valueOf(computer.getCompany().getId()),computer.getCompany().getName());
		
		
		DTOComputer dtoComputer = new DTOComputer(id,name,introduced,discontinued,dtoCompany);
		return dtoComputer;
	}

	public static  int countResults(ResultSet resultSet) {
		try {
			return resultSet.getInt(1);
		} catch (SQLException e) {
			logger.error("result set in countResults");
			e.printStackTrace();
		}
		return 0;
	}

	public static LocalDate stringToDate(String sDate) {

		return (sDate == null) ? null : LocalDate.parse(sDate);

	}

}

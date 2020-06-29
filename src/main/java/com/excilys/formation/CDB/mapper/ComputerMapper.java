package com.excilys.formation.CDB.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.model.Computer;

public class ComputerMapper {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static Computer processResults(ResultSet resultSet) {

		try {
			long ID = resultSet.getLong(1);
			String name = resultSet.getString("name");
			LocalDate ldIntroduced = (resultSet.getString("introduced") == null) ? null
					: LocalDate.parse(resultSet.getString("introduced"), dateFormatter);
			LocalDate ldDiscontinued = (resultSet.getString("discontinued")== null)? null:
					LocalDate.parse(resultSet.getString("discontinued"), dateFormatter);
			Long company_ID = resultSet.getLong("company_id");

			Computer c = new Computer(ID, name, ldIntroduced, ldDiscontinued, company_ID);
			return c;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}
	
	public static DTOComputer ComputerToDTO(Computer computer) {
		
		
		String name = computer.getName();
		String introduced = computer.getIntroduced()==null?"":computer.getIntroduced().toString();
		String discontinued = computer.getDiscontinued()==null?"":computer.getDiscontinued().toString();
		String company_id = computer.getCompanyID().toString();
		
		
		DTOComputer DTO = new DTOComputer(name,introduced,discontinued,company_id);
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

	public static Computer stringTabToComputer(String[] computerInfo) {

		if (computerInfo.length != 4) {
			System.out.println("probleme dans computerInfo pas 4 elements");
		}
		String name = computerInfo[0];

		LocalDate introduced = (computerInfo[1] == null) ? null : LocalDate.parse(computerInfo[1], dateFormatter);

		LocalDate discontinued = (computerInfo[2] == null) ? null : LocalDate.parse(computerInfo[2], dateFormatter);

		Long company_id = (computerInfo[3] == null) ? null : Long.parseLong(computerInfo[3]);

		return new Computer(name, introduced, discontinued, company_id);

	}

	public static LocalDate stringToDate(String sDate) {

		return (sDate == null) ? null : LocalDate.parse(sDate);

	}

}

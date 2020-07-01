package com.excilys.formation.CDB.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.formation.CDB.DTO.DTOCompany;
import com.excilys.formation.CDB.DTO.DTOComputer;
import com.excilys.formation.CDB.model.Company;
import com.excilys.formation.CDB.model.Computer;

public class ComputerMapper {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static Computer processResults(ResultSet resultSet) {

		try {
			Long id = resultSet.getLong(1);
			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC              "+id);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
//
//	public static Computer stringTabToComputer(String[] computerInfo) {
//
//		if (computerInfo.length != 4) {
//			System.out.println("probleme dans computerInfo pas 4 elements");
//		}
//		String name = computerInfo[0];
//
//		LocalDate introduced = (computerInfo[1] == null) ? null : LocalDate.parse(computerInfo[1], dateFormatter);
//
//		LocalDate discontinued = (computerInfo[2] == null) ? null : LocalDate.parse(computerInfo[2], dateFormatter);
//
//		Long company_id = (computerInfo[3] == null) ? null : Long.parseLong(computerInfo[3]);
//		
//		
//
//		return new Computer(name, introduced, discontinued, company_id);
//
//	}

	public static LocalDate stringToDate(String sDate) {

		return (sDate == null) ? null : LocalDate.parse(sDate);

	}

}
